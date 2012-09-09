allthethings: debug install logcat

debug:
	ant debug

clean:
	ant clean

install:
	adb -d install -r bin/cen3031-debug.apk

logcat:
	adb logcat | grep "TheGame"
