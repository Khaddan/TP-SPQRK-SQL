package org.example;

import org.apache.spark.sql.*;

import java.util.Date;
import java.util.List;
import org.apache.spark.sql.functions.*;

import static org.apache.spark.sql.functions.*;

public class application2 {
    public static void main(String[] args) throws AnalysisException {
        SparkSession ss= SparkSession.builder()
                .appName("TP SparkSQL")
                .master("local[*]")
                .getOrCreate();
       Dataset<Row> dsInc= ss.read().option("header","true").option("inferSchema","true").csv("incidents.csv");
       dsInc.printSchema();
       dsInc.show();
       dsInc.createTempView("incidents");
       ss.sql("select service , count(titre) as nomberInc from incidents group by service").show();
       Dataset<Row> dsIncY= dsInc.withColumn("year" , split(col("date"),"-").getItem(0));
       dsIncY.show();
       dsIncY.createOrReplaceTempView("incidents");
       Dataset<Row> dsInc1=ss.sql("select year, count(titre) as nomberInc  from incidents group by year order by count(titre) desc ");
       dsInc1.show(2);
    }
}
