package com.capstone.badi.model
import com.opencsv.CSVReaderBuilder
import java.io.Reader

class Parser {

    companion object {

        fun toDataSet(reader: Reader): List<SalesForcasting> {

            val csvReader = CSVReaderBuilder(reader)
                .withSkipLines(1)
                .build()

            val foodSearches = mutableListOf<SalesForcasting>()
            var record = csvReader.readNext()

            while (record != null) {
                foodSearches.add(SalesForcasting(record[0]))
                record = csvReader.readNext()
            }

            return foodSearches
        }
    }
}
