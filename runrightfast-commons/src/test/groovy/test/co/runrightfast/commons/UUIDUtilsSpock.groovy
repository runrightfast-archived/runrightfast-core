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

import co.runrightfast.commons.UUIDUtils
import spock.lang.Specification

class UUIDUtilsSpock extends Specification {
   def "expect UUIDs to not contain any '-' chars"() {
      expect :
      !UUIDUtils.uuid().contains("-")
   }

   def "expect UUID length to be 32 chars"(){
      expect:
      UUIDUtils.uuid().length() == 32
   }
}
