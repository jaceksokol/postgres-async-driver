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

package com.github.pgasync.impl.message;

import lombok.Value;

/**
 * @author Antti Laisi
 */
@Value
public class CommandComplete implements Message {
    int updatedRows;

    public CommandComplete(String tag) {
        if (tag.contains("INSERT") || tag.contains("UPDATE") || tag.contains("DELETE")) {
            String[] parts = tag.split(" ");
            updatedRows = Integer.parseInt(parts[parts.length - 1]);
        } else {
            updatedRows = 0;
        }
    }
}
