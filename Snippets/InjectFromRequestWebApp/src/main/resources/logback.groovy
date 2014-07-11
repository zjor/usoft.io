appender("CONSOLE", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
		pattern = "%logger{35} %X{sessionId} - %msg%n"
	}
}

logger("io.usoft", INFO, ["CONSOLE"])
