package com.example.chaosemeraldservice.controller;

import com.example.chaosemeraldservice.exception.EmeraldCreationFailedException;
import com.example.chaosemeraldservice.exception.EmeraldException;
import com.example.chaosemeraldservice.exception.EmeraldNotUpdatedException;
import com.example.chaosemeraldservice.exception.EmeraldUpdateFailedException;
import com.example.chaosemeraldservice.model.Colour;
import com.example.chaosemeraldservice.model.Emerald;
import com.example.chaosemeraldservice.service.EmeraldService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class EmeraldControllerTest {

    private EmeraldService emeraldService;

    private EmeraldController emeraldController;

    @Before
    public void setUp() {
        this.emeraldService = mock(EmeraldService.class);
        this.emeraldController = new EmeraldController(this.emeraldService);
    }

    @Test
    public void shouldGetEmeraldWithSuccess() throws Exception {
        Emerald actual = mock(Emerald.class);
        when(this.emeraldService.getEmerald(1L)).thenReturn(actual);

        ResponseEntity<Emerald> result = this.emeraldController.getEmerald(1L);

        assertThat(actual, is(result.getBody()));
        assertThat(HttpStatus.OK, is(result.getStatusCode()));
        verify(this.emeraldService).getEmerald(1L);
        verifyZeroInteractions(this.emeraldService);
    }

    @Test(expected = EmeraldException.class)
    public void shouldNotGetEmeraldWithException() throws Exception {
        when(this.emeraldService.getEmerald(1L)).thenReturn(null);

        try {
            this.emeraldController.getEmerald(1L);
        } catch (Exception e) {
            verify(this.emeraldService).getEmerald(1L);
            verifyZeroInteractions(this.emeraldService);
            throw e;
        }
    }

    @Test
    public void shouldGetAllEmeraldsWithSuccess() throws Exception {
        List<Emerald> emeraldList = Collections.emptyList();
        when(this.emeraldService.getEmeralds()).thenReturn(emeraldList);

        ResponseEntity<List<Emerald>> allEmeraldsResponse = this.emeraldController.getAllEmeralds();

        assertThat(emeraldList, is(allEmeraldsResponse.getBody()));
        assertThat(HttpStatus.OK, is(allEmeraldsResponse.getStatusCode()));
        verify(this.emeraldService).getEmeralds();
        verifyNoMoreInteractions(this.emeraldService);
    }

    @Test(expected = EmeraldException.class)
    public void shouldNotGetAllEmeraldsWithException() throws Exception {
        when(this.emeraldService.getEmeralds()).thenReturn(null);

        try {
            this.emeraldController.getAllEmeralds();
        } catch (Exception e) {
            verify(this.emeraldService).getEmeralds();
            verifyNoMoreInteractions(this.emeraldService);
            throw e;
        }
    }

    @Test
    public void shouldCreateEmeraldWithSuccess() throws Exception {
        Emerald requestEmerald = new Emerald(7L, Colour.YELLOW);
        Emerald createdEmerald = spy(requestEmerald);
        when(createdEmerald.getId()).thenReturn(9L);
        when(this.emeraldService.createEmerald(7L, Colour.YELLOW)).thenReturn(createdEmerald);

        ResponseEntity<Emerald> emeraldResponse = this.emeraldController.postEmerald(requestEmerald);

        assertThat(createdEmerald, is(emeraldResponse.getBody()));
        assertThat(HttpStatus.CREATED, is(emeraldResponse.getStatusCode()));
        verify(this.emeraldService).createEmerald(7L, Colour.YELLOW);
        verifyNoMoreInteractions(this.emeraldService);
    }

    @Test(expected = EmeraldCreationFailedException.class)
    public void shouldNotCreateEmeraldWithException() throws Exception {
        Emerald requestEmerald = new Emerald(7L, Colour.YELLOW);
        when(this.emeraldService.createEmerald(7L, Colour.YELLOW)).thenReturn(null);

        try {
            this.emeraldController.postEmerald(requestEmerald);
        } catch (Exception e) {
            verify(this.emeraldService).createEmerald(7L, Colour.YELLOW);
            verifyNoMoreInteractions(this.emeraldService);
            throw e;
        }
    }

    @Test
    public void shouldUpdateEmeraldWithSuccess() throws Exception {
        Emerald requestEmerald = spy(new Emerald(99L, Colour.RED));
        when(requestEmerald.getId()).thenReturn(99L);
        when(this.emeraldService.updateEmerald(requestEmerald)).thenReturn(requestEmerald);

        ResponseEntity<Emerald> emeraldResponse = this.emeraldController.putEmerald(99L, requestEmerald);

        assertThat(HttpStatus.CREATED, is(emeraldResponse.getStatusCode()));
        assertThat(requestEmerald, is(emeraldResponse.getBody()));
        verify(this.emeraldService).updateEmerald(requestEmerald);
        verifyNoMoreInteractions(this.emeraldService);
    }

    @Test(expected = EmeraldUpdateFailedException.class)
    public void shouldNotUpdateEmeraldWithException() throws Exception {
        Emerald requestEmerald = spy(new Emerald(99L, Colour.RED));
        when(requestEmerald.getId()).thenReturn(99L);
        when(this.emeraldService.updateEmerald(requestEmerald)).thenReturn(null);

        try {
            this.emeraldController.putEmerald(99L, requestEmerald);
        } catch (Exception e) {
            verify(this.emeraldService).updateEmerald(requestEmerald);
            verifyNoMoreInteractions(this.emeraldService);
            throw e;
        }
    }

    @Test(expected = EmeraldNotUpdatedException.class)
    public void shouldNotUpdateEmeraldWithIdConflict() throws Exception {
        Emerald requestEmerald = spy(new Emerald(99L, Colour.RED));
        when(requestEmerald.getId()).thenReturn(9L);

        try {
            this.emeraldController.putEmerald(99L, requestEmerald);
        } catch (Exception e) {
            verifyNoMoreInteractions(this.emeraldService);
            throw e;
        }
    }

    @Test
    public void shouldDeleteEmeraldWithSuccess() throws Exception {

        ResponseEntity responseEntity = this.emeraldController.deleteEmerald(9L);

        assertThat(HttpStatus.NO_CONTENT, is(responseEntity.getStatusCode()));
        verify(this.emeraldService).deleteEmerald(9L);
        verifyNoMoreInteractions(this.emeraldService);
    }
}