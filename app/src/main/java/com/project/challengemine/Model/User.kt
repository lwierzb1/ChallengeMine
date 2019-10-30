package com.project.challengemine.Model

class User {
    var uid: String? = null;
    var name: String? = null;
    var email: String? = null;
    var acceptList: HashMap< String, User >? = null;
    var statistics: Statistics? = null;

    constructor(){};

    constructor(uid: String, email:String, name:String ) {
        this.uid = uid;
        this.email = email;
        this.name = name
        this.acceptList = HashMap();

        this.statistics = Statistics()
    }

    constructor(uid: String, email:String, name:String, statistics: Statistics ) {
        this.uid = uid;
        this.email = email;
        this.name = name
        this.acceptList = HashMap();

        this.statistics = statistics;
    }


}