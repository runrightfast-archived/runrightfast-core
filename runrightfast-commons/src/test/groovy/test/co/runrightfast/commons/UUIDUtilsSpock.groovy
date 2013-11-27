package test.co.runrightfast.commons

import co.runrightfast.commons.UUIDUtils;
import spock.lang.Specification

class UUIDUtilsSpock extends Specification {
	def "expect UUIDs to not contain any '-' chars"() {
		
		expect :
		 !UUIDUtils.uuid().contains("-")		
		
	}

}
