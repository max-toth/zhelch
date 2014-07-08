#!/bin/bash

if [[ $1 == 'build' ]]; then
	echo 'remove target path'
	rm -r target
	echo 'build target...'
	mkdir target
	`mkdir target/classes`
	echo 'compile *.java sources'
	`javac client/*.java -d target/classes`	
	`jar cfe target/zhc.jar client.Client target/classes`	
fi

if [[ $1 == 'run' ]]; then
	`java -jar target/zhc.jar`
fi

if [[ $1 == '' ]]; then
	echo 'no args!'
fi