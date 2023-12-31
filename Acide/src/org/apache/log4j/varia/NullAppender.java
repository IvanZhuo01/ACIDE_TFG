/*
 * Copyright 1999-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.log4j.varia;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.OptionHandler;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.AppenderSkeleton;

/**
  * A NullAppender merely exists, it never outputs a message to any
  * device.  
  * @author Ceki G&uuml;lc&uml;
  */
@SuppressWarnings("unused")
public class NullAppender extends AppenderSkeleton {

  private static NullAppender instance = new NullAppender();

  public NullAppender() {
  }

  /** 
   * There are no options to acticate.
   * */
  public void activateOptions() {
  }

  /**
   * Whenever you can, use this method to retreive an instance instead
   * of instantiating a new one with <code>new</code>.
   * */
  public NullAppender getInstance() {
    return instance;
  }

  public void close() {
  }

  /**
   * Does not do anything. 
   * */
  public void doAppend(LoggingEvent event) {
  }

  /**
   * Does not do anything. 
   * */
  protected void append(LoggingEvent event) {
  }

  /**
    * NullAppenders do not need a layout.  
    * */
  public boolean requiresLayout() {
    return false;
  }
}
