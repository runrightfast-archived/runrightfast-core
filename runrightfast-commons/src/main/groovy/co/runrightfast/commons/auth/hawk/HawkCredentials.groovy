package co.runrightfast.commons.auth.hawk

import groovy.json.JsonSlurper
import groovy.transform.Immutable

@Immutable
class HawkCredentials {

   static enum Algorithm {
      sha1,
      sha256
   }

   String id
   String key
   Algorithm algorithm

   static HawkCredentials fromJson(String json) {
      JsonSlurper slurper = new JsonSlurper()
      return new HawkCredentials(slurper.parseText(json))
   }
}
