package model

import org.springframework.stereotype.Component

/**
 * Created by tinguan on 19/11/16.
 */
@Component
class CSVExtractor {

    public List<HashMap> extract(String path) {
        def csv = new File(path)
        List<HashMap> csvData = new ArrayList<>()
        csv.splitEachLine(',') { row ->
            csvData += ["name": row.get(0).replace("\"", ""), "date": row.get(1).replace("\"", ""), "floor" : row.get(2).replace("\"", "")]
        }
        return csvData
    }
}