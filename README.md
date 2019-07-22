# Playground Api
## Install on STS
[Install lombok](https://stackoverflow.com/questions/35842751/lombok-not-working-with-sts)
```
. locate ~/.m2/repository/org/projectlombok/lombok/version.x
. java -jar lombok-1.x.y.jar
. restart/start STS
```
Fix [javax.xml.bind](https://github.com/eclipse-ee4j/jaxb-ri/issues/1235)
```
<!-- https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api -->
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.0-b170201.1204</version>
</dependency>
<!-- https://mvnrepository.com/artifact/javax.activation/activation -->
<dependency>
    <groupId>javax.activation</groupId>
    <artifactId>activation</artifactId>
    <version>1.1</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime -->
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
</dependency>
```