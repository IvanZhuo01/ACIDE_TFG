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

package org.apache.log4j.helpers;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
   
  An always-empty Enumerator.

  @author Anders Kristensen
  @since version 1.0
 */

@SuppressWarnings({"rawtypes"})
public class NullEnumeration implements Enumeration {
  private static final NullEnumeration instance = new NullEnumeration();
  
  private
  NullEnumeration() {
  }
  
  public static NullEnumeration getInstance() {
    return instance;
  }
  
  public
  boolean hasMoreElements() {
    return false;
  }
  
  public
  Object nextElement() {
    throw new NoSuchElementException();
  }
}
