# springTest
spring3 + spring mvc + mybatis
# 第三章
```
 1、lookup-method方法例子(包springBean.lookup)
 通过在spirng配置文件中配置lookup-method,可以动态地切换新的业务类
```
# 更新
## 2018-09-22 
### 1、spring版本变更
- spring3
- spring5

### 2、增加junit
```
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.3.1</version>
    <scope>test</scope>
</dependency>

<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-test</artifactId>
  <version>${spring.version}</version>
  <scope>test</scope>
</dependency>
```

### 3、增加日志
- pom.xml增加日志所需要的包
```
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>

<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.11.1</version>
</dependency>

<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-api</artifactId>
  <version>2.11.1</version>
</dependency>

<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-web</artifactId>
  <version>2.11.1</version>
</dependency>

<dependency> <!-- 桥接：告诉Slf4j使用Log4j2 -->
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-slf4j-impl</artifactId>
  <version>2.11.1</version>
</dependency>
```

- web.xml增加log4j2日志配置
> 如果web.xml是servlet3.0,则不需要配置，会自动查找，否则需要配置。
```
<listener>
    <listener-class>org.apache.logging.log4j.web.Log4jServletContextListener</listener-class>
</listener>
<filter>
    <filter-name>log4jServletFilter</filter-name>
    <filter-class>org.apache.logging.log4j.web.Log4jServletFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>log4jServletFilter</filter-name>
    <url-pattern>/*</url-pattern>
    <dispatcher>REQUEST</dispatcher>
    <dispatcher>FORWARD</dispatcher>
    <dispatcher>INCLUDE</dispatcher>
    <dispatcher>ERROR</dispatcher>
</filter-mapping>
```

### 4、zip、rar解压缩
- pom.xml增加tar解压缩包
```
<dependency>
    <groupId>org.apache.ant</groupId>
    <artifactId>ant</artifactId>
    <version>1.9.4</version>
</dependency>
```

### 5、ftp连接池
```
<dependency>
    <groupId>commons-net</groupId>
    <artifactId>commons-net</artifactId>
    <version>3.6</version>
</dependency>
```

### 6、jdbc连接池
```
<dependency>
    <groupId>commons-dbcp</groupId>
    <artifactId>commons-dbcp</artifactId>
    <version>1.4</version>
</dependency>
```




