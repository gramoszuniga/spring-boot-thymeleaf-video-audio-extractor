
# spring-boot-thymeleaf-video-audio-extractor

Demo project for a web application that extracts audio from uploaded videos using FFmpeg and FFprobe.


## Tech Stack

**Server:** Java 17, FFmpeg, FFprobe, Jaffree, Spring Boot, Thymeleaf


## Run Locally

Clone the project

```bash
  git clone https://github.com/gramoszuniga/spring-boot-thymeleaf-video-audio-extractor
```

Go to the project directory

```bash
  cd spring-boot-thymeleaf-video-audio-extractor
```

Install dependencies

```bash
  mvn clean install
```

Start the server

```bash
  mvn package
  java -jar target/spring-boot-thymeleaf-video-audio-extractor-1.0.0.jar
```

Or use docker

```bash
  docker build -t spring-boot-thymeleaf-video-audio-extractor/latest .
  docker run -p 8080:8080 spring-boot-thymeleaf-video-audio-extractor/latest
```


## Acknowledgements

- [Linux FFmpeg and FFprobe binaries](https://johnvansickle.com/ffmpeg/)
- [macOS FFmpeg and FFprobe binaries](https://evermeet.cx/ffmpeg/)

