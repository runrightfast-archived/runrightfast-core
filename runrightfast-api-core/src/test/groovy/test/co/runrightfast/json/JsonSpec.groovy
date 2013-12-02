package test.co.runrightfast.json

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import spock.lang.Specification
@Log
class JsonSpec extends Specification {

   @Log
   static class Person {
      String fname, lname

      def getName() {
         def name = fname + ' ' + lname
         log.info(name)
         name
      }
   }

   def "toJson() - can marshal an Object to JSON"() {
      setup:
      Person person = new Person(fname:'Alfio', lname:'Zappala')
      JsonSlurper slurper = new JsonSlurper()

      when:
      String json = JsonOutput.toJson(person)
      log.info('person json: ' + json)
      def person2 = slurper.parseText(json)

      then:
      person2.fname == person.fname
      person2.lname == person.lname
   }
}
