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
package co.runrightfast.commons

abstract class BeanUtils {

   /**
    * Enables properties and methods to be invoked on the supplied target in a more groovy way
    * 
    * @param target
    * @param code
    * @return target
    */
   def static exec(@DelegatesTo.Target target,@DelegatesTo code){
      def clone = code.rehydrate(target, this, this)
      clone()
      target
   }
}
