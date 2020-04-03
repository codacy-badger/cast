# cast
### 简介
Cast 是一个将指定的 Object 转换为目标类型的工具类库。

### 使用
```java
// 将指定对象转换为 Character
Cast.toChar(object, defaultValue);
// 对于不需提供默认值参数的方法，在无法完成转换时将会抛出异常
// 如下方法均提供了这两种方式
Cast.toChar(object);
// 将指定的对象转换为字符串，如果要转换的对象为数组或集合类型，会将其元素拼接起来，用英文逗号分隔
// eg: int[]{1, 2, 3} => "1,2,3"
// Clob => String
Cast.toStr(object, defaultValue);
// 将指定对象转换为 Boolean，如果指定对象为字符串
// "true"、"on"、"yes"、"1" 将转换为 true
// "false"、"off"、"no"、"0" 将转换为 false
Cast.toBool(object, defaultValue);

Cast.toByte(object, defaultValue);
Cast.toShort(object, defaultValue);
Cast.toInteger(object, defaultValue);
Cast.toLong(object, defaultValue);
Cast.toFloat(object, defaultValue);
Cast.toDouble(object, defaultValue);
Cast.toBigInteger(object, defaultValue);
Cast.toBigDecimal(object, defaultValue);

// Blob => byte array
// InputStream => byte array
Cast.toBytes(object, defaultValue);

// 将指定的对象转换为 java.util.Date
Cast.toDate(object, defaultValue);
// 该方法允许提供一个格式化的字符串 format，用于如果指定对象为字符串，会根据此格式解析字符串到对应的 Date
Cast.toDate(object, format, defaultValue);
Cast.toSqlDate(object, defaultValue);
Cast.toSqlTimestamp(object, defaultValue);
Cast.toSqlTime(object, defaultValue);

// 将指定对象转换为指定类型 targetClass 的枚举
Cast.toEnum(object, targetClass, defaultValue);

Cast.toArray(collection, elementTargetClass, defaultValue);

Cast.toCharset(string, defaultValue);

Cast.toTimeZone(string, defaultValue);
```

### 安装
#### Maven
在项目的 pom.xml 文件中的 dependencies 节点下添加如下内容
```xml
<dependency>
    <groupId>com.github.jonzhang3</groupId>
    <artifactId>cast</artifactId>
    <version>1.1.0</version>
</dependency>
```