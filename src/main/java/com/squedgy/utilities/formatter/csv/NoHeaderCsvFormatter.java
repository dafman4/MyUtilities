package com.squedgy.utilities.formatter.csv;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import static com.squedgy.utilities.formatter.csv.CsvFormatter.EscapableCharacters.getEscapeChar;
import static org.slf4j.LoggerFactory.getLogger;

public class NoHeaderCsvFormatter extends CsvFormatter<List<List<String>>> {

    private static Logger log = getLogger(NoHeaderCsvFormatter.class);

    @Override
    public List<List<String>> encode(InputStream stream) throws IOException {
        int currentChar = 0;
        StringBuilder builder = new StringBuilder();
        List<List<String>> ret = new LinkedList<>();

        try{
            skipInitialRows(stream, builder);
        } catch (IOException e) {
            log.error("Error occurred before finished skipping the initial rows, returning no-results.", e);
            return ret;
        }

        builder.setLength(0);
        if(quoteChar != null) {
            log.debug("quoted");
            ret = getQuotedValues(stream, builder);
        } else {
            log.debug("un-quoted");
            ret = getUnquotedValues(stream, builder);
        }

        if(log.isDebugEnabled()) log.debug(ret.size() + " columns");

        int size = ret.get(0).size();
        if(!ret.stream().allMatch(list -> list.size() == size)) {
            // If the amount of columns in each row aren't equal, UH OH
            for(int i = 0; i < ret.size(); i++) log.debug("Column #" + i + ": "+ ret.get(i).size());
            throw new IllegalArgumentException("The passed stream didn't have equal columns for every row!");
        } else if(size > ignoreLast) { // If there's more records than are ignored
            if(ignoreLast > 0) {
                // Cut down the lists
                int ignored = ignoreLast;
                ret = ret.stream().map(list -> list.subList(0, size - ignored)).collect(Collectors.toList());
            }
        } else {
            ret.forEach(List::clear);
        }

        return ret;
    }

    @Override
    public InputStream decode(List<List<String>> toDecode) {

        StringBuilder builder = new StringBuilder();

        String delimiter = "" + (quoteChar == null ? this.delimiter : quoteChar + this.delimiter + quoteChar);
        int addLineF = toDecode.size() - 1;

        if(toDecode.size() > 0) {
            for (int i = 0; i < toDecode.get(0).size(); i++) {
                for (int f = 0; f < toDecode.size(); f ++) {
                    builder.append(toDecode.get(f).get(i));
                    if(f == addLineF) builder.append(lineSeparator);
                    else builder.append(delimiter);
                }
            }
        }

        InputStream ret = new ByteArrayInputStream(builder.toString().getBytes(Charset.forName("UTF-8")));
        return null;
    }

    private void skipInitialRows(InputStream stream, StringBuilder builder) throws IOException {
        int rowsSkipped = 0;
        while(rowsSkipped < skipRows) {
            builder.append((char)stream.read());
            if(builder.length() >= lineSeparator.length()-1 && builder.substring(builder.length()-lineSeparator.length()).equals(lineSeparator)){
                rowsSkipped += 1;
                if(log.isDebugEnabled()) {
                    log.debug("finished another line:\n\n" + builder.toString());
                }
            }
        }

    }

    private List<List<String>> getQuotedValues(InputStream stream, StringBuilder builder) {
        List<List<String>> ret = new LinkedList<>();
        int column = 0, currentChar;
        inQuote = false;
        StringBuilder lineEnds = new StringBuilder();

        while(maxSelection <= 0 || ret.size() <= maxSelection) {
            try {
                currentChar = stream.read();
                if(currentChar == -1) {
                    // Don't skimp on it if its only the first row
                    if(column == ret.size()-1 || ret.get(0).size() <= 1) addValue(ret, column, builder.toString());
                    break;
                }
            } catch (IOException e) {
                log.error("An Error occurred while reading a value, end of stream?", e);
                if(column == ret.size()-1 || ret.get(0).size() <= 1) addValue(ret, column, builder.toString());
                break;
            }

            if(lineEnd(lineEnds, currentChar)){
                if(column == ret.size()-1 || ret.get(0).size() <= 1) addValue(ret, column, builder.toString());
                column = 0;
                builder.setLength(0);
            } else {
                lineEnds.append((char) currentChar);
                if(inQuote) {

                    if(currentChar == escapeChar) {
                        // if escaping we need to validate and get the escaped char
                        builder.append((char) getEscapeChar(currentChar, stream));
                    } else if (currentChar == quoteChar) {
                        inQuote = false;
                    } else {
                        builder.append((char) currentChar);
                    }

                } else {

                    if(currentChar == quoteChar) {
                        if(builder.length() != 0) throw new IllegalStateException("Column " + column + " on line " + (ret.size() > 0 ? ret.get(ret.size()-1).size() : 1) + " has multiple values in the same column!");
                        inQuote = true;
                    } else if (currentChar == delimiter) {
                        // We're at the next value after this so add the current one
                        addValue(ret, column++, builder.toString());
                        // And reset the builder
                        builder.setLength(0);
                    }

                }
            }

        }

        return ret;
    }

    private List<List<String>> getUnquotedValues(InputStream stream, StringBuilder builder) {
        List<List<String>> ret = new LinkedList<>();
        int column = 0, currentChar;

        while(maxSelection <= 0 || ret.size() <= maxSelection) {
            try {
                currentChar = stream.read();
                if(currentChar == -1) {
                    if(builder.length() > 0)
                    addValue(ret, column, builder.toString());
                    break;
                }
            } catch (IOException e) {
                log.error("An Error occured while reading a value, end of stream?", e);
                // If it's the EOS we need to add, if it's something else we'll probably catch it elsewhere with formatting errors
                addValue(ret, column, builder.toString());
                break;
            }

            if(lineEnd(builder, currentChar)){
                // New lines we need to ignore the ending lineSeparator
                addValue(ret, column, getBuilderValue(builder, lineSeparator.length()));
                // And reset the column count
                column = 0;
                // And reset the builder
                builder.setLength(0);
            } else if(currentChar == escapeChar) {
                currentChar = readChar(stream);
                if(currentChar == delimiter) builder.append(delimiter);
                // if escaping we need to validate and get the escaped char
                else builder.append((char) getEscapeChar(currentChar, stream));
            } else if (currentChar == delimiter) {
                // We're at the next value after this so add the current one
                addValue(ret, column++, builder.toString());
                // And reset the builder
                builder.setLength(0);
            } else {
                // If nothing else just add the char!
                builder.append((char) currentChar);
            }

        }


        return ret;
    }

    protected String getBuilderValue(StringBuilder builder, int except) {
        if(builder.length() > except) {
            return builder.substring(0, builder.length() - except);
        } else {
            return "";
        }
    }

    protected <ValueType> void addValue(List<List<ValueType>> values, int column, ValueType value) {
        if(values.size() == column) {
            values.add(new LinkedList<>());
        }

        values.get(column).add(value);
    }

}
