/**
 * Copyright 2013 RunRightFast.co
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.co.runrightfast.commons

import groovy.util.logging.Slf4j
import spock.lang.Specification
import co.runrightfast.commons.BeanUtils

class BeanUtilsSpec extends Specification {

   @Slf4j
   static class Person{
      def String fname, lname

      def getName() {
         def _name = fname + ' ' + lname
         log.info(_name)
         _name
      }
   }

   def "exec should be able to set a bean properties in a DSL fashion"() {
      setup:
      def person = new Person()

      when:
      BeanUtils.exec(person){
         fname = "Alfio"
         lname = "Zappala"
      }

      then :
      person.fname == "Alfio"
      person.lname == "Zappala"
      person.name == "Alfio Zappala"
   }

   def "exec should be able to set a bean properties in a DSL fashion and returns the target"() {
      when:
      def person =  BeanUtils.exec(new Person()){
         fname = "Alfio"
         lname = "Zappala"
      }

      then :
      person.fname == "Alfio"
      person.lname == "Zappala"
   }
}
