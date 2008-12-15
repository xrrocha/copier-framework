#PS=;
PS=:
#CLASSPATH="$CLASSPATH${PS}../build/classes/main"
CLASSPATH="$CLASSPATH${PS}../build/record.jar"

for lib  in ../lib/*.jar
do
    CLASSPATH="$CLASSPATH${PS}$lib"
done

export CLASSPATH
