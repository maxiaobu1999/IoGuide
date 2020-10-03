在Java中有输入、输出两种IO流，每种输入、输出流又分为字节流和字符流两大类。

关于字节，每个字节(byte)有8bit组成。
关于字符，我们可能知道代表一个汉字或者英文字母。
二、File
文件的获取
```$xslt
//构造函数File(String pathname)
File f1 =new File("c:\\abc\\1.txt");
//File(String parent,String child)
File f2 =new File("c:\\abc","2.txt");
//File(File parent,String child)
File f3 =new File("c:"+File.separator+"abc");//separator 跨平台分隔符
File f4 =new File(f3,"3.txt");
System.out.println(f1);//c:\abc\1.txt
```
文件的创建以及删除
```$xslt
//如果文件存在返回false，否则返回true并且创建文件 
boolean createNewFile();
//创建一个File对象所对应的目录，成功返回true，否则false。且File对象必须为路径而不是文件。只会创建最后一级目录，如果上级目录不存在就抛异常。
boolean mkdir();
//创建一个File对象所对应的目录，成功返回true，否则false。且File对象必须为路径而不是文件。创建多级目录，创建路径中所有不存在的目录
boolean mkdirs()    ;
//如果文件存在返回true并且删除文件，否则返回false
boolean delete();
//在虚拟机终止时，删除File对象所表示的文件或目录。
void deleteOnExit();
```
判断方法
```$xslt
boolean canExecute()    ;//判断文件是否可执行
boolean canRead();//判断文件是否可读
boolean canWrite();//判断文件是否可写
boolean exists();//判断文件是否存在
boolean isDirectory();//判断是否是目录
boolean isFile();//判断是否是文件
boolean isHidden();//判断是否是隐藏文件或隐藏目录
boolean isAbsolute();//判断是否是绝对路径 文件不存在也能判断
```
获取参数方法
```$xslt
String getName();//返回文件或者是目录的名称
String getPath();//返回路径
String getAbsolutePath();//返回绝对路径
String getParent();//返回父目录，如果没有父目录则返回null
long lastModified();//返回最后一次修改的时间
long length();//返回文件的长度
File[] listRoots();// 列出所有的根目录（Window中就是所有系统的盘符）
String[] list() ;//返回一个字符串数组，给定路径下的文件或目录名称字符串
String[] list(FilenameFilter filter);//返回满足过滤器要求的一个字符串数组
File[]  listFiles();//返回一个文件对象数组，给定路径下文件或目录
File[] listFiles(FilenameFilter filter);//返回满足过滤器要求的一个文件对象数组
```
File[] listFiles(FilenameFilter filter);//返回满足过滤器要求的一个文件对象数组
其中FilenameFileter 是一个文件过滤器。找到所有png图片。
```$xslt
        file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });
```

RandomAccessFile
可以支持文件的随机访问，程序快可以直接跳转到文件的任意地方来读写数据。所以如果需要访问文件的部分内容，而不是把文件从头读到尾，使用RandomAccessFile将是更好的选择。
**"r" : ** 以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException。
"rw": 打开以便读取和写入。
"rws": 打开以便读取和写入。相对于 "rw"，"rws" 还要求对“文件的内容”或“元数据”的每个更新都同步写入到基础存储设备。
"rwd" : 打开以便读取和写入，相对于 "rw"，"rwd" 还要求对“文件的内容”的每个更新都同步写入到基础存储设备。

方法摘要
void close() ：关闭此随机访问文件流并释放与该流关联的所有系统资源。

FileChannel getChannel() ： 返回与此文件关联的唯一 FileChannel 对象。

FileDescriptor getFD() ：返回与此流关联的不透明文件描述符对象。

long getFilePointer() ：返回此文件中的当前偏移量。

long length() ：返回此文件的长度。

int read() ：从此文件中读取一个数据字节。

int read(byte[] b) ： 将最多 b.length 个数据字节从此文件读入 byte 数组。

int read(byte[] b, int off, int len) ：将最多 len 个数据字节从此文件读入 byte 数组。

boolean readBoolean() ：从此文件读取一个 boolean。

byte readByte() ：从此文件读取一个有符号的八位值。

char readChar() ：从此文件读取一个字符。

double readDouble() ：从此文件读取一个 double。

float readFloat() ：从此文件读取一个 float。

void readFully(byte[] b) ： 将 b.length 个字节从此文件读入 byte 数组，并从当前文件指针开始。

void readFully(byte[] b, int off, int len) ：将正好 len 个字节从此文件读入 byte 数组，并从当前文件指针开始。

int readInt() ：从此文件读取一个有符号的 32 位整数。

String readLine() ：从此文件读取文本的下一行。

long readLong() ： 从此文件读取一个有符号的 64 位整数。

short readShort() ：从此文件读取一个有符号的 16 位数。

int readUnsignedByte() ：从此文件读取一个无符号的八位数。

int readUnsignedShort() ：从此文件读取一个无符号的 16 位数。

String readUTF() ：从此文件读取一个字符串。

void seek(long pos) ：设置到此文件开头测量到的文件指针偏移量，在该位置发生下一个读取或写入操作。

void setLength(long newLength) ：设置此文件的长度。

int skipBytes(int n) ：尝试跳过输入的 n 个字节以丢弃跳过的字节。

void write(byte[] b) ： 将 b.length 个字节从指定 byte 数组写入到此文件，并从当前文件指针开始。

void write(byte[] b, int off, int len) ：将 len 个字节从指定 byte 数组写入到此文件，并从偏移量 off 处开始。

void write(int b) ：向此文件写入指定的字节。

void writeBoolean(boolean v) ：按单字节值将 boolean 写入该文件。

void writeByte(int v) ：按单字节值将 byte 写入该文件。

void writeBytes(String s) ： 按字节序列将该字符串写入该文件。

void writeChar(int v) ：按双字节值将 char 写入该文件，先写高字节。

void writeChars(String s) ： 按字符序列将一个字符串写入该文件。

void writeDouble(double v) ：使用 Double 类中的 doubleToLongBits 方法将双精度参数转换为一个 long，然后按八字节数量将该 long 值写入该文件，先定高字节。

void writeFloat(float v) ：使用 Float 类中的 floatToIntBits 方法将浮点参数转换为一个 int，然后按四字节数量将该 int 值写入该文件，先写高字节。

void writeInt(int v) ：按四个字节将 int 写入该文件，先写高字节。

void writeLong(long v) ：按八个字节将 long 写入该文件，先写高字节。

void writeShort(int v) ：按两个字节将 short 写入该文件，先写高字节。

void writeUTF(String str) ：使用 modified UTF-8 编码以与机器无关的方式将一个字符串写入该文件。









1、分片下载 断点续传
2、配置http框架
3、前台服务 通知
4、开始下载 取消 暂停 重新下载 继续下载
5、内存映射文件开关（下载用不上，上传）
6、同步/异步
文件校验
回调监听

service 线程

service 延时关闭  通知延时关闭


request 分片数量

支持uri适配sdk29

https://docs.developer.amazonservices.com/zh_CN/dev_guide/DG_MD5.html
完整性校验 响应头Content-MD5（只能16位base64编码） / ETag（缓存验证的，可有可无，放个时间戳吧）
是否检查更新

数据库主键是自增好还是UUID好
sqlite 多线程读写 ：一个SQLiteDataBase对象多线程读写ok，多个SQLiteDataBase抛android.database.sqlite.SQLiteCantOpenDatabaseException: unable to open database file (Sqlite code 14), (OS error - 24:Too many open files)

流量提示 仅Wi-Fi下载阀值
失败控制：删除记录/续传

数据库设计 （DatabaseHelper）

_id     INTEGER PRIMARY KEY AUTOINCREMENT 自增
download_url TEXT          下载地址
description_uri TEXT          保存地址
description_filepath  TEXT    保存地址
mime_type TEXT    下载数据的MIME类型 
last_mod COLUMN_LAST_MODIFICATION  BIGINT, 最后一次修改时间
status  INTEGER 当前下载状态 暂停正在下载
total_bytes COLUMN_TOTAL_BYTES + " BIGINT, 下载的文件总大小
current_bytes COLUMN_CURRENT_BYTES + " BIGINT 当前下载的文件大小
header TEXT 请求头 map2json
speed TEXT 下载速度
etag    ETAG TEXT 缓存校验md5
allowed_network 什么状态可以下载 Wi-Fi 流量 漫游
auto_resume BOOLEAN 条件允许时，自动开始下载（资源预下载）
priority    INTEGER 下载优先级

entity  TEXT 应用程序特定数据 eg：/data/user/0/com.norman.malong/cache/test10Mb.apk
no_integrity BOOLEAN   开启完整性校验 包含指示启动应用程序是否能够验证下载文件完整性的标志的列的名称。设置此标志后，下载管理器执行下载并报告成功，即使在某些情况下它不能保证下载已完成（例如，在没有ETag的情况下执行字节范围请求，或无法确定下载是否完全完成时）
_data TEXT 下载存储路径


separate  BOOLEAN 使用分片
separate_num  INTEGER  分片数量
separate_done  INTEGER  已完成的分片数量

title   COLUMN_TITLE + " TEXT, " 该下载的标题 request.setTitle("下载图片.sad..")
description COLUMN_DESCRIPTION + " TEXT, "下载描述
media_scanned BOOLEAN   下载完成，进行媒体扫描，让别的应用可以发现文件
notification_class   COLUMN_NOTIFICATION_CLASS + " TEXT, "   点通知跳哪里,类名全路径
notification_extras COLUMN_NOTIFICATION_EXTRAS + " TEXT, " 跳转传数据








