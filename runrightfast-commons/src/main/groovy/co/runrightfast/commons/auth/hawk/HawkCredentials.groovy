package co.runrightfast.commons.auth.hawk

import groovy.json.JsonSlurper
import groovy.transform.Immutable
import co.runrightfast.commons.UUIDUtils

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
      new HawkCredentials(slurper.parseText(json))
   }

   static HawkCredentials create(Algorithm algorithm = Algorithm.sha256) {
      new HawkCredentials(UUIDUtils.uuid(), UUIDUtils.uuid(), algorithm)
   }
}
