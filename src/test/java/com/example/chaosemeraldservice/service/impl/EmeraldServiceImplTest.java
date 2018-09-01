package com.example.chaosemeraldservice.service.impl;

import com.example.chaosemeraldservice.exception.*;
import com.example.chaosemeraldservice.model.Colour;
import com.example.chaosemeraldservice.model.Emerald;
import com.example.chaosemeraldservice.persistence.EmeraldRepository;
import com.example.chaosemeraldservice.service.EmeraldService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmeraldServiceImplTest {

    private EmeraldRepository emeraldRepository;
    private EmeraldService emeraldService;

    @Before
    public void setUp() {
        this.emeraldRepository = mock(EmeraldRepository.class);
        this.emeraldService = new EmeraldServiceImpl(this.emeraldRepository);
    }

    @Test
    public void shouldGetEmeraldWhenIdPresent() throws Exception {
        Emerald actual = mock(Emerald.class);
        Optional<Emerald> emeraldOptional = Optional.of(actual);
        when(this.emeraldRepository.findById(1L)).thenReturn(emeraldOptional);

        Emerald result = this.emeraldService.getEmerald(1L);

        assertThat(actual, is(result));
        verify(this.emeraldRepository).findById(1L);
        verifyNoMoreInteractions(this.emeraldRepository);
    }

    @Test(expected = EmeraldNotFoundException.class)
    public void shouldReturnExceptionWhenIdNotPresent() throws Exception {
        Optional<Emerald> emeraldOptional = Optional.empty();
        when(this.emeraldRepository.findById(1L)).thenReturn(emeraldOptional);

        assertGetEmeraldWithException(1L);
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnExceptionWhenNullOptionalReturned() throws Exception {
        when(this.emeraldRepository.findById(3L)).thenReturn(null);

        assertGetEmeraldWithException(3L);
    }

    @Test
    public void shouldReturnEmptyListOfEmeralds() throws Exception {
        when(this.emeraldRepository.findAll()).thenReturn(Collections.emptyList());

        List<Emerald> emeraldList = this.emeraldService.getEmeralds();

        assertThat(emeraldList.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnListOfEmeralds() throws Exception {
        Emerald emerald1 = mock(Emerald.class);
        Emerald emerald2 = mock(Emerald.class);
        Emerald emerald3 = mock(Emerald.class);
        when(this.emeraldRepository.findAll()).thenReturn(Arrays.asList(emerald1, emerald2, emerald3));

        List<Emerald> emeraldList = this.emeraldService.getEmeralds();

        assertThat(emeraldList.isEmpty(), is(false));
        assertThat(emeraldList.size(), is(3));
        assertThat(emeraldList.contains(emerald1), is(true));
        assertThat(emeraldList.contains(emerald2), is(true));
        assertThat(emeraldList.contains(emerald3), is(true));
    }

    @Test
    public void shouldUpdateEmeraldWhenEmeraldExistsAndNewValuesProvided() throws Exception {
        Emerald requestEmerald = new Emerald(100L, Colour.BLUE);
        Emerald existingEmerald = new Emerald(200L, Colour.GREEN);
        when(this.emeraldRepository.findById(1L)).thenReturn(Optional.of(existingEmerald));
        when(this.emeraldRepository.save(requestEmerald)).thenReturn(requestEmerald);

        Emerald result = this.emeraldService.updateEmerald(1L, requestEmerald);

        assertThat(requestEmerald, is(result));
        assertNotNull(result);
        assertEquals(requestEmerald, result);
        verify(this.emeraldRepository).findById(1L);
        verify(this.emeraldRepository).save(requestEmerald);
        verifyNoMoreInteractions(this.emeraldRepository);
    }

    @Test(expected = EmeraldNotUpdatedException.class)
    public void shouldNotUpdateEmeraldWithExceptionWhenEmeraldExistsAndValuesMatch() throws Exception {
        Emerald requestEmerald = new Emerald(100L, Colour.BLUE);
        Emerald existingEmerald = new Emerald(100L, Colour.BLUE);
        when(this.emeraldRepository.findById(9L)).thenReturn(Optional.of(existingEmerald));

        assertUpdateEmeraldWithException(requestEmerald);
    }

    @Test(expected = EmeraldNotFoundException.class)
    public void shouldNotUpdateEmeraldWithExceptionWhenEmeraldDoesNotExist() throws Exception {
        Emerald requestEmerald = spy(new Emerald(100L, Colour.BLUE));
        when(this.emeraldRepository.findById(1L)).thenReturn(Optional.empty());

        assertUpdateEmeraldWithException(requestEmerald);
    }

    @Test(expected = EmeraldUpdateFailedException.class)
    public void shouldThrowExceptionWhenEmeraldDoesNotSave() throws Exception {
        Emerald requestEmerald = spy(new Emerald(100L, Colour.BLUE));
        Emerald existingEmerald = new Emerald(200L, Colour.GREEN);
        when(this.emeraldRepository.findById(1L)).thenReturn(Optional.of(existingEmerald));
        when(this.emeraldRepository.save(requestEmerald)).thenReturn(null);

        try {
            this.emeraldService.updateEmerald(1L, requestEmerald);
        } catch (Exception e) {
            verify(this.emeraldRepository).findById(1L);
            verify(this.emeraldRepository).save(existingEmerald);
            verifyNoMoreInteractions(this.emeraldRepository);
            throw e;
        }
    }

    @Test
    public void shouldCreateEmerald() throws Exception {
        Emerald actual = mock(Emerald.class);
        when(this.emeraldRepository.save(any())).thenReturn(actual);

        Emerald result = this.emeraldService.createEmerald(9L, Colour.WHITE);

        assertThat(actual, is(result));
        verify(this.emeraldRepository).save(any());
        verifyNoMoreInteractions(this.emeraldRepository);
    }

    @Test(expected = EmeraldCreationFailedException.class)
    public void shouldThrowExceptionWhenFailedToCreateEmerald() throws Exception {
        when(this.emeraldRepository.save(any())).thenReturn(null);

        try {
            this.emeraldService.createEmerald(9L, Colour.WHITE);
        } catch (Exception e) {
            verify(this.emeraldRepository).save(any());
            verifyNoMoreInteractions(this.emeraldRepository);
            throw e;
        }
    }

    @Test
    public void shouldDeleteEmerald() throws Exception {
        when(this.emeraldRepository.findById(4L)).thenReturn(Optional.empty());

        this.emeraldService.deleteEmerald(4L);

        verify(this.emeraldRepository).deleteById(4L);
        verify(this.emeraldRepository).findById(4L);
        verifyNoMoreInteractions(this.emeraldRepository);
    }

    @Test(expected = EmeraldDeletionFailedException.class)
    public void shouldThrowExceptionIfUnableToDeleteEmerald() throws Exception {
        Emerald actual = spy(new Emerald(100L, Colour.BLUE));
        when(this.emeraldRepository.findById(4L)).thenReturn(Optional.of(actual));

        this.emeraldService.deleteEmerald(4L);

        verify(this.emeraldRepository).deleteById(4L);
        verify(this.emeraldRepository).findById(4L);
        verifyNoMoreInteractions(this.emeraldRepository);
    }

    private void assertGetEmeraldWithException(Long id) throws Exception {
        try {
            this.emeraldService.getEmerald(id);
        } catch (Exception e) {
            verify(this.emeraldRepository).findById(id);
            verifyNoMoreInteractions(this.emeraldRepository);
            throw e;
        }
    }

    private void assertUpdateEmeraldWithException(Emerald emerald) throws Exception {
        try {
            this.emeraldService.updateEmerald(9L, emerald);
        } catch (Exception e) {
            verify(this.emeraldRepository).findById(9L);
            verifyNoMoreInteractions(this.emeraldRepository);
            throw e;
        }
    }
}