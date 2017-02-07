import org.apache.ignite.configuration._
import org.apache.ignite.spark._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Anton Solovev
  * @since 08.02.17.
  */
object HelloWorld extends Serializable {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("spark://172.24.0.2:7077")
      .setAppName("demo")
    val sc = SparkContext.getOrCreate(conf)

    val ic = new IgniteContext(sc, () => new IgniteConfiguration())

    val sharedRDD = ic.fromCache[Integer, Integer]("partitioned")
    sharedRDD.filter(_._2 < 10).collect()
    sharedRDD.savePairs(sc.parallelize(1 to 100000, 10).map(i => (i, i)))
    sharedRDD.filter(_._2 > 50000).count
  }
}
