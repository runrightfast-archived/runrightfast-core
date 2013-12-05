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

class HawkCredentialsServlet extends HttpServlet {
   final String entityType = 'HawkCredentials'
   final String propId = 'id'
   final String propKey = 'key'
   final String propAlgorithm = 'algorithm'

   DatastoreService datastore

   @Override
   void init() {
      datastore = DatastoreServiceFactory.datastoreService
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
      String requestUri = req.requestURI
      String id = requestUri[(requestUri.lastIndexOf('/') + 1)..(requestUri.length() - 1)]
      try {
         Entity entity = datastore.get(KeyFactory.createKey(entityType, id))
         HawkCredentials credentials = toHawkCredentials(entity)
         resp.writer.print(JsonOutput.toJson(credentials))
      } catch (EntityNotFoundException e) {
         resp.status = HttpServletResponse.SC_NOT_FOUND
         resp.writer.print(e.message)
      }
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
      HawkCredentials credentials = HawkCredentials.create()
      datastore.put(toEntity(credentials))
      resp.contentType = 'application/json'
      resp.writer.print(JsonOutput.toJson(credentials))
   }

   private Entity toEntity(HawkCredentials credentials) {
      Entity entity = new Entity(entityType,credentials.id)
      entity.setProperty(propId, credentials.id)
      entity.setProperty(propKey, credentials.key)
      entity.setProperty(propAlgorithm, credentials.algorithm.name())
      entity
   }

   private HawkCredentials toHawkCredentials(Entity entity) {
      String id = entity.getProperty(propId)
      String key = entity.getProperty(propKey)
      HawkCredentials.Algorithm algorithm = HawkCredentials.Algorithm.valueOf(entity.getProperty(propAlgorithm))
      new HawkCredentials(id, key, algorithm)
   }
}
