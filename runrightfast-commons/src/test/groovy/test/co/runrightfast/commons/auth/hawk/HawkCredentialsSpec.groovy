package test.co.runrightfast.commons.auth.hawk

import groovy.json.JsonOutput
import groovy.util.logging.Log
import spock.lang.Specification
import co.runrightfast.commons.UUIDUtils
import co.runrightfast.commons.auth.hawk.HawkCredentials

@Log
class HawkCredentialsSpec extends Specification {

   def "is immutable"() {
      setup:
      String id = UUIDUtils.uuid()
      String key = UUIDUtils.uuid()
      HawkCredentials creds = new HawkCredentials(id, key, HawkCredentials.Algorithm.sha256)
      log.info(creds.toString())

      when:
      creds.id = UUIDUtils.uuid()

      then:
      thrown(ReadOnlyPropertyException)

      when:
      creds.key = UUIDUtils.uuid()

      then:
      thrown(ReadOnlyPropertyException)

      when:
      creds.algorithm = HawkCredentials.Algorithm.sha1

      then:
      thrown(ReadOnlyPropertyException)
   }

   def "the constructor arg order is id, key, algorithm"() {
      setup:
      String id = UUIDUtils.uuid()
      String key = UUIDUtils.uuid()
      HawkCredentials creds = new HawkCredentials(id, key, HawkCredentials.Algorithm.sha256)
      log.info(creds.toString())

      expect:
      creds.id == id
      creds.key == key
      creds.algorithm == HawkCredentials.Algorithm.sha256
   }

   def "can be marshalled back and forth to JSON"() {
      setup:
      String id = UUIDUtils.uuid()
      String key = UUIDUtils.uuid()
      HawkCredentials creds = new HawkCredentials(id, key, HawkCredentials.Algorithm.sha256)
      log.info(creds.toString())

      when:
      String json = JsonOutput.toJson(creds)
      HawkCredentials creds2 = HawkCredentials.fromJson(json)
      log.info('creds2 : ' + creds2.toString())

      then:
      creds.id == creds2.id
      creds.key == creds2.key
      creds.algorithm == creds2.algorithm
   }
}
