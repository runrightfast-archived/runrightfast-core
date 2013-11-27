package co.runrightfast.commons

abstract class UUIDUtils {

	def static String uuid() {
		UUID.randomUUID().toString().replace('-', '');
	}
}
