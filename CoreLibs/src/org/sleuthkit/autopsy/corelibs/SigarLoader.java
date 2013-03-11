/*
 * Autopsy Forensic Browser
 *
 * Copyright 2013 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
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
package org.sleuthkit.autopsy.corelibs;

import java.io.File;
import org.hyperic.sigar.Sigar;

/**
 * Wrapper over Sigar instrumentation class to facilitate dll loading. Our setup
 * bypasses Sigar library loader which does not work well for netbeans
 * environment We are responsible for loading the library ourselves.
 */
public class SigarLoader {

    private static volatile Sigar sigar;

    static {
        //bypass the process of validation/loading of the library by sigar jar
        System.setProperty("org.hyperic.sigar.path", "-");
        //System.setProperty(org.hyperic.sigar.SigarLoader.PROP_SIGAR_JAR_NAME, "sigar-1.6.4.jar");
    }

    public static Sigar getSigar() {
        synchronized (SigarLoader.class) {
            if (sigar == null) {
                try {
                    //rely on netbeans / jna to locate the lib variation for architecture/OS
                    System.loadLibrary("libsigar");
                    sigar = new Sigar();
                    sigar.enableLogging(false); //forces a test

                } catch (UnsatisfiedLinkError ex) {
                    System.out.println("Error loading sigar library" + ex.toString());
                } catch (Exception ex) {
                    System.out.println("Error loading sigar library" + ex.toString());
                }
            }
        }

        return sigar;
    }
}
