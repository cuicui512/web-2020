>IDEA快捷键
>1\ctrl + shift + n:快速查找所需文件
>2\Ctrl + shift +F12:编码区域最大化，返回也是一样
>3\shift + F6对文件进行更改名字
>4\Alt + insert：实现setter \ getter方法
### 使用flyway创建数据表
>必须先安装 mavn

### mybatis 管理数据表命令
>mvn flyway:migrate:根据文件建立表（文件目录：resource>db>migration）
>mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate(根据数据库表创建Mapper和实体对象)