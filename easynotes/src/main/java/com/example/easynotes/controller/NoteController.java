package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {
	@Autowired
	NoteRepository noteRepository;
	
	@GetMapping("/notes")
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note){
		return noteRepository.save(note);
	}
	
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value="id") Integer id){
		return noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note","id",id));
	}
	
	@PostMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value="id") Integer id, @Valid @RequestBody Note note){
		Note noteDB = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
		
		noteDB.setContent(note.getContent());
		noteDB.setTitle(note.getTitle());
		return noteRepository.save(noteDB);
		
	}
	
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value="id") Integer id){
		Note orElseThrow = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
		noteRepository.delete(orElseThrow);
		return ResponseEntity.ok().build();
	}
	
	
}
