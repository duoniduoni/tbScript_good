ant -buildfile /home/zhoangsong/workspace/tbScript
adb push /home/zhoangsong/workspace/tbScript/bin/taobaotest.jar /data/local/tmp/
#adb shell uiautomator runtest taobaotest.jar -c com.uiautomatortest.Test#returnResult -e args 牦牛酸奶*青海藏家自制牦牛酸奶#游客吃过都说好*TRUE*1*1
