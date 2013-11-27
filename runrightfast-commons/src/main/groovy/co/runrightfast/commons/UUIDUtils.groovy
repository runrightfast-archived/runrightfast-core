package co.runrightfast.commons

abstract class UUIDUtils {

	/**
	 * 
	 * @return uuid with '-' stripped out to conserve bytes
	 */
	static String uuid() {
		UUID.randomUUID().toString().replace('-', '');
	}
}
