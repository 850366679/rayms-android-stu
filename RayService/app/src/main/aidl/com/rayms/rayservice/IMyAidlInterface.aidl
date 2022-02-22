package com.rayms.rayservice;

import com.rayms.rayservice.Person;

interface IMyAidlInterface {
  void addPerson(in Person person);

  List<Person> getPersonList();
}