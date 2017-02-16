#!/bin/sh
java=java
if test -n "$JAVA_HOME"; then
    java="$JAVA_HOME/bin/java"
fi
exec "$java" -cp "lib/*" bg.plambis.dict.gui.DictionaryGUI
