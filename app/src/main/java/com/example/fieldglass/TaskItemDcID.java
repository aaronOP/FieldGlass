package com.example.fieldglass;

import androidx.annotation.NonNull;

public class TaskItemDcID {

        public String DOCUMENTID;

        public <F extends TaskItemDcID>  F withId(@NonNull final String id) {
            this.DOCUMENTID = id;
            return (F) this;

        }
    }
