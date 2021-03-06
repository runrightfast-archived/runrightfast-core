package co.runrightfast.api.core.web.servlets.auth

import groovy.json.JsonOutput

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import co.runrightfast.commons.auth.hawk.HawkCredentials

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.DatastoreServiceFactory
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.EntityNotFoundException
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.memcache.MemcacheService
import com.google.appengine.api.memcache.MemcacheServiceFactory

class HawkCredentialsServlet extends HttpServlet {
   static final String ENTITY_TYPE = 'HawkCredentials'
   static final String PROP_KEY = 'key'
   static final String PROP_ALGORITHM = 'algorithm'

   DatastoreService datastore
   MemcacheService memcache

   @Override
   void init() {
      datastore = DatastoreServiceFactory.datastoreService
      memcache = MemcacheServiceFactory.memcacheService
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
      String requestUri = req.requestURI
      String id = requestUri[(requestUri.lastIndexOf('/') + 1)..(requestUri.length() - 1)]

      HawkCredentials credentials = getFromMemcache(id)
      if (credentials == null) {
         credentials = getFromDatastore(id)
         if (credentials != null) {
            memcache.put(id, credentials)
         }
      }

      if (credentials != null) {
         resp.writer.print(JsonOutput.toJson(credentials))
      } else {
         resp.status = HttpServletResponse.SC_NOT_FOUND
         resp.writer.print("Credentials were not found for: ${id}")
      }
   }

   private HawkCredentials getFromDatastore(String id) {
      HawkCredentials creds
      try {
         creds = toHawkCredentials(datastore.get(KeyFactory.createKey(ENTITY_TYPE, id)))
         creds
      } catch (EntityNotFoundException e) {
         creds
      }
   }

   private HawkCredentials getFromMemcache(String id) {
      (HawkCredentials)memcache.get(id)
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
      HawkCredentials credentials = HawkCredentials.create()
      datastore.put(toEntity(credentials))
      resp.contentType = 'application/json'
      resp.writer.print(JsonOutput.toJson(credentials))
   }

   private Entity toEntity(HawkCredentials credentials) {
      Entity entity = new Entity(ENTITY_TYPE,credentials.id)
      entity.setProperty(PROP_KEY, credentials.key)
      entity.setProperty(PROP_ALGORITHM, credentials.algorithm.name())
      entity
   }

   private HawkCredentials toHawkCredentials(Entity entity) {
      String id = entity.key.name
      String key = entity.getProperty(PROP_KEY)
      HawkCredentials.Algorithm algorithm = HawkCredentials.Algorithm.valueOf(entity.getProperty(PROP_ALGORITHM))
      new HawkCredentials(id, key, algorithm)
   }
}
