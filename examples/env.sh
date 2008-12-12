#PS=;
PS=:
CLASSPATH="$CLASSPATH${PS}../build/classes/main"

for lib  in ../lib/*.jar
do
    CLASSPATH="$CLASSPATH${PS}$lib"
done

export CLASSPATH
