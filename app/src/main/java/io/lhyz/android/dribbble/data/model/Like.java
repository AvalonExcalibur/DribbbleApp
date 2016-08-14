/*
 * Copyright (c) 2016 lhyz Android Open Source Project
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
package io.lhyz.android.dribbble.data.model;

import java.io.Serializable;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
@SuppressWarnings("unused")
public class Like implements Serializable {
    int id;
    String created_at;

    public Like(int id, String created_at) {
        this.id = id;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }
}
