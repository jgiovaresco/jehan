// -----------------------------------------------------------------------------
// Projet : jehan
// Client : Pôle Emploi
// Auteur : giovarej / Bull S.A.S.
// -----------------------------------------------------------------------------
package com.github.jehan.model;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * Defines a Jenkins job.
 */
public class Job implements Serializable
{
   // ------------------------- private constants -------------------------

   // ------------------------- private members-------------------------

   /** The name. */
   private String m_name;

   /** The url. */
   private String m_url;

   /** The color. */
   private String m_color;

   // ------------------------- public methods -------------------------

   @Override
   public String toString()
   {
      return Objects.toStringHelper(this).add("name", m_name).add("url", m_url).add("color", m_color).toString();
   }

   /**
    * Returns the name.
    * @return The name.
    */
   public String getName()
   {
      return m_name;
   }

   /**
    * Sets the name.
    * @param p_name The name.
    */
   public void setName(String p_name)
   {
      m_name = p_name;
   }

   /**
    * Returns the url.
    * @return The url.
    */
   public String getUrl()
   {
      return m_url;
   }

   /**
    * Sets the url.
    * @param p_url The url.
    */
   public void setUrl(String p_url)
   {
      m_url = p_url;
   }

   /**
    * Returns the color.
    * @return The color.
    */
   public String getColor()
   {
      return m_color;
   }

   /**
    * Sets the color.
    * @param p_color The color.
    */
   public void setColor(String p_color)
   {
      m_color = p_color;
   }
}