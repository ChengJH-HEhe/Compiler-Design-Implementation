# Change the src to the path of your java source files
JAVA_SRC = $(shell find src -name '*.java')
# Change this to the path of your antlr jar
ANTLR_JAR = /ulib/antlr-4.13.0-complete.jar
LOMBOK_JAR = $(CURDIR)/lombok.jar
.PHONY: all
all: build

.PHONY: build
build: $(JAVA_SRC)
	javac -d bin $(JAVA_SRC) -cp $(ANTLR_JAR):$(LOMBOK_JAR) -encoding UTF-8

.PHONY: clean
clean:
	find bin -name '*.class' -or -name '*.jar' | xargs rm -f
.PHONY: run
run: 
	java -cp bin:$(ANTLR_JAR):$(LOMBOK_JAR) juhuh.compiler.Main