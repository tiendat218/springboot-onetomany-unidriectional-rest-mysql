package com.example.springbootonetomanyunidriectionalrestmysql.repository;

import com.example.springbootonetomanyunidriectionalrestmysql.jpa.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILibraryRepository extends JpaRepository<Library,Integer> {
}
