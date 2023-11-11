
# Multithreaded Image Processing

This Java program loads an image file, divides it into squares of a specified size, then calculates the average color of each square, and applies the average color to the all pixels inside that particular square. It employs single-threading as well as multithreading to process different portions of the image concurrently to improve performance. The resulting image is real-time updated in a Swing GUI window, and the elapsed time for each thread's or single thread's execution is printed to the console for performance comparison.
## Requirements

**Java (JDK):** https://www.oracle.com/java/technologies/downloads/


## Run Locally

Clone the project

```bash
  git clone https://github.com/ADA-GWU/3-concurrency-piabor.git
```

Go to the project directory

```bash
  cd ./3-concurrency-piabor
```

Generate runnable .class files from .java files

```bash
  javac -sourcepath ./src/ -d ./out/ ./src/ImageProcessingApplication.java
```

Move an image to the project directory. The program requires 3 arguments:
1. **Name and extension of the image file**
2. **Square size:** side length of a square that will be used to calculate and apply the average color
3. **Processing mode:** S for Single-threaded and M for Multithreaded. Multithreading processes the image concurrently by the number of available physical processors.

Here is an example command to run the program:

```bash
  java -cp ./out/ ImageProcessingApplication image.jpg 20 M
```

