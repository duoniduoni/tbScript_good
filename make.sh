ant -buildfile /home/zhoangsong/workspace/Demo1
adb push /home/zhoangsong/workspace/Demo1/bin/taobaotest.jar /data/local/tmp/
adb shell uiautomator runtest taobaotest.jar -c com.uiautomatortest.Test#testDemo2 -e args 鹦鹉活体*中大型说话鹦鹉#绿身红领大型鹦鹉#包活包健*TRUE*30*20
