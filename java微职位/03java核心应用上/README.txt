使用方法:
 
0. 先下载压缩包解压后得到jetbrains-agent.jar，把它放到你认为合适的文件夹内。

1. 点击你要注册的IDE菜单："Configure|Help" -> "Edit Custom VM Options ..."
    如果提示是否要创建文件，请点"是|Yes"

2. 在打开的vmoptions编辑窗口末行添加："-javaagent:/absolute/path/to/jetbrains-agent.jar"
    一定要自己确认好路径，填错会导致IDE打不开！！！最好使用绝对路径。
    如: -javaagent:/Users/carlosxiao/jetbrains-agent.jar

3. 重启你的IDE。
 
4. 注册选择Activation Code方式，填入ACTIVATION_CODE.txt 内的注册码激活, 既可以使用idea了。