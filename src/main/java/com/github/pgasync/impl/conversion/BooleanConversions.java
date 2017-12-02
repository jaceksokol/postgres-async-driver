/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pgasync.impl.conversion;

import com.github.pgasync.SqlException;
import com.github.pgasync.impl.Oid;

/**
 * @author Antti Laisi
 */
class BooleanConversions {
    ;

    static final byte[] TRUE  = new byte[]{ 't' };
    static final byte[] FALSE = new byte[]{ 'f' };

    static boolean toBoolean(Oid oid, byte[] value) {
        switch (oid) {
            case UNSPECIFIED: // fallthrough
            case BOOL:
                return TRUE[0] == value[0];
            default:
                throw new SqlException("Unsupported conversion " + oid.name() + " -> boolean");
        }
    }

    static byte[] fromBoolean(boolean value) {
        return value ? TRUE : FALSE;
    }
}
