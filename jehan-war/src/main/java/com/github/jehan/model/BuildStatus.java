package com.github.jehan.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * Defines the build status.
 */
public enum BuildStatus
{
   /** Successul build. */
   SUCCESS("blue"),
   /** Failed build. */
   FAILED("red"),
   /** Test Failed build. */
   TEST_FAILED("yellow"),
   /** Disabled build. */
   DISABLED("disabled") ,
   /** Inactive build. */
   INACTIVE("grey");

   // ------------------------- private members-------------------------

   /** The color of the build. */
   private String m_color;

   // ------------------------- constructor -------------------------

   /**
    * Constructor.
    * @param p_color The color.
    */
   private BuildStatus(String p_color)
   {
      m_color = p_color;
   }

   // ------------------------- public methods -------------------------

   /**
    * Get the enum from a String value.
    * @param p_value The value.
    * @return The enum or null if no enum corresponding.
    */
   @JsonCreator
   public static BuildStatus forValue(String p_value)
   {
      BuildStatus buildStatus = null;

      BuildStatus[] values = values();
      for (int i = 0; i < values.length || null == buildStatus; i++)
      {
         if (values[i].m_color.equals(p_value))
         {
            buildStatus = values[i];
         }
      }

      return buildStatus;
   }

   /**
    * Returns a String representation of the enum.
    * @return The color corresponding to the enum.
    */
   @JsonValue
   public String toValue()
   {
      return m_color;
   }
}
