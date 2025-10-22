package org.wadajo.turismomadrid.domain.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.wadajo.turismomadrid.application.repository.AlojamientoRepository;
import org.wadajo.turismomadrid.domain.document.AlojamientoDocument;
import org.wadajo.turismomadrid.domain.dto.cmadrid.enums.TipoAlojamiento;
import org.wadajo.turismomadrid.domain.exception.TipoNoValidoException;
import org.wadajo.turismomadrid.domain.model.AlojamientoTuristico;
import org.wadajo.turismomadrid.infrastructure.mapper.AlojamientoDocumentMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.wadajo.turismomadrid.domain.dto.cmadrid.enums.TipoAlojamiento.*;
import static org.wadajo.turismomadrid.infrastructure.constants.Constants.CONTADO_UN;

@ApplicationScoped
public class TurismoService {

    private final AlojamientoRepository alojamientoRepository;

    private final AlojamientosService alojamientosService;

    private final AlojamientoDocumentMapper alojamientoDocumentMapper;

    public TurismoService(AlojamientoRepository alojamientoRepository, AlojamientoDocumentMapper alojamientoDocumentMapper, AlojamientosService alojamientosService) {
        this.alojamientoRepository = alojamientoRepository;
        this.alojamientoDocumentMapper = alojamientoDocumentMapper;
        this.alojamientosService = alojamientosService;
    }

    public List<AlojamientoTuristico> getAlojamientosTuristicosEnRemoto() {
        var listaRaw = alojamientosService.getAlojamientosTotales();
        generarMapaConLaCuenta(listaRaw);
        return listaRaw;
    }

    public String guardarTodosLosAlojamientosRemotosEnDb(){
        List<AlojamientoTuristico> todosLosAlojamientosEnRemoto = alojamientosService.getAlojamientosTotales();
        Log.infof("Total alojamientos turísticos en origen: %d", todosLosAlojamientosEnRemoto.size());
        AtomicLong cuenta = new AtomicLong();

        var apartamentosRuralesDocumentList = new ArrayList<AlojamientoDocument>();
        var apartTuristicosDocumentList = new ArrayList<AlojamientoDocument>();
        var campingsDocumentList = new ArrayList<AlojamientoDocument>();
        var casasHuespedesDocumentList = new ArrayList<AlojamientoDocument>();
        var casasRuralesDocumentList = new ArrayList<AlojamientoDocument>();
        var hostalesDocumentList = new ArrayList<AlojamientoDocument>();
        var hosteriasDocumentList = new ArrayList<AlojamientoDocument>();
        var hotelesDocumentList = new ArrayList<AlojamientoDocument>();
        var hotelesApartDocumentList = new ArrayList<AlojamientoDocument>();
        var hotelesRuralesDocumentList = new ArrayList<AlojamientoDocument>();
        var pensionesDocumentList = new ArrayList<AlojamientoDocument>();
        var viviendasTuristicasDocumentList = new ArrayList<AlojamientoDocument>();

        for (AlojamientoTuristico unAlojamientoEnRemoto : todosLosAlojamientosEnRemoto) {
            cuenta.incrementAndGet();
            switch (unAlojamientoEnRemoto.alojamiento_tipo()) {
                case APART_TURISTICO ->
                    apartTuristicosDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case APARTAMENTO_RURAL ->
                    apartamentosRuralesDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case CAMPING ->
                    campingsDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case CASA_HUESPEDES ->
                    casasHuespedesDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case CASA_RURAL ->
                    casasRuralesDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case HOSTAL ->
                    hostalesDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case HOSTERIAS ->
                    hosteriasDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case HOTEL ->
                    hotelesDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case HOTEL_APART ->
                    hotelesApartDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case HOTEL_RURAL ->
                    hotelesRuralesDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case PENSION ->
                    pensionesDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
                case VIVIENDAS_TURISTICAS ->
                    viviendasTuristicasDocumentList.add(convertToAlojamientoDocument(unAlojamientoEnRemoto));
            }
        }

        alojamientoRepository.persist(apartamentosRuralesDocumentList);
        Log.infof("Guardados en DB %s apartamentos rurales.", apartamentosRuralesDocumentList.size());
        alojamientoRepository.persist(apartTuristicosDocumentList);
        Log.infof("Guardados en DB %s apart turísticos.", apartTuristicosDocumentList.size());
        alojamientoRepository.persist(campingsDocumentList);
        Log.infof("Guardados en DB %s campings.", campingsDocumentList.size());
        alojamientoRepository.persist(casasHuespedesDocumentList);
        Log.infof("Guardados en DB %s casas de huéspedes.", casasHuespedesDocumentList.size());
        alojamientoRepository.persist(casasRuralesDocumentList);
        Log.infof("Guardados en DB %s casas rurales.", casasRuralesDocumentList.size());
        alojamientoRepository.persist(hostalesDocumentList);
        Log.infof("Guardados en DB %s hostales.", hostalesDocumentList.size());
        alojamientoRepository.persist(hosteriasDocumentList);
        Log.infof("Guardados en DB %s hosterías.", hosteriasDocumentList.size());
        alojamientoRepository.persist(hotelesDocumentList);
        Log.infof("Guardados en DB %s hoteles.", hotelesDocumentList.size());
        alojamientoRepository.persist(hotelesApartDocumentList);
        Log.infof("Guardados en DB %s hoteles apart.", hotelesApartDocumentList.size());
        alojamientoRepository.persist(hotelesRuralesDocumentList);
        Log.infof("Guardados en DB %s hoteles rurales.", hotelesRuralesDocumentList.size());
        alojamientoRepository.persist(pensionesDocumentList);
        Log.infof("Guardados en DB %s pensiones.", pensionesDocumentList.size());
        alojamientoRepository.persist(viviendasTuristicasDocumentList);
        Log.infof("Guardados en DB %s viviendas turísticas.", viviendasTuristicasDocumentList.size());

        generarMapaConLaCuenta(todosLosAlojamientosEnRemoto);

        return "Han sido guardados en DB: "+ cuenta+" alojamientos.";
    }

    private AlojamientoDocument convertToAlojamientoDocument(AlojamientoTuristico alojamientoTuristicoEnRemoto) {
        return alojamientoDocumentMapper.convert(alojamientoTuristicoEnRemoto);
    }

    public void borrarTodo() {
        alojamientoRepository.deleteAll();
        Log.info("Borrada alojamientos");
    }

    public List<AlojamientoTuristico> getAlojamientosByType(String tipo) throws TipoNoValidoException {
        try {
            var tipoValido=TipoAlojamiento.valueOf(tipo);
            var listaFiltrada = alojamientosService.getAlojamientosTotales().stream()
                .filter(alojamientoTuristico -> alojamientoTuristico.alojamiento_tipo() == tipoValido)
                .toList();
            generarMapaConLaCuenta(listaFiltrada);
            return listaFiltrada;
        } catch (IllegalArgumentException e) {
            Log.errorf("Tipo de alojamiento turístico no válido: %s", tipo);
            throw new TipoNoValidoException(String.format("Tipo de alojamiento turístico no válido: %s",tipo), e);
        }
    }

    public String borrarTodosLosAlojamientosEnDb() {
        borrarTodo();
        return "Borrados";
    }

    private static void generarMapaConLaCuenta(List<AlojamientoTuristico> listaFinal) {
        HashMap<String, AtomicLong> mapa=new HashMap<>();
        AtomicLong apartamentosRurales = new AtomicLong();
        AtomicLong apartTuristicos = new AtomicLong();
        AtomicLong campings = new AtomicLong();
        AtomicLong casasHuespedes = new AtomicLong();
        AtomicLong casasRurales = new AtomicLong();
        AtomicLong hostales = new AtomicLong();
        AtomicLong hosterias = new AtomicLong();
        AtomicLong hoteles = new AtomicLong();
        AtomicLong apartHoteles = new AtomicLong();
        AtomicLong hotelesRurales = new AtomicLong();
        AtomicLong pensiones = new AtomicLong();
        AtomicLong viviendasTuristicas = new AtomicLong();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            listaFinal
                .forEach(unAlojamiento ->
                    executor.submit(() -> {
                        switch (unAlojamiento.alojamiento_tipo()){
                            case APARTAMENTO_RURAL -> logAndCount(apartamentosRurales, TipoAlojamiento.APARTAMENTO_RURAL);
                            case APART_TURISTICO -> logAndCount(apartTuristicos, TipoAlojamiento.APART_TURISTICO);
                            case CAMPING -> logAndCount(campings, TipoAlojamiento.CAMPING);
                            case CASA_HUESPEDES -> logAndCount(casasHuespedes, TipoAlojamiento.CASA_HUESPEDES);
                            case  CASA_RURAL -> logAndCount(casasRurales, TipoAlojamiento.CASA_RURAL);
                            case HOSTAL -> logAndCount(hostales, TipoAlojamiento.HOSTAL);
                            case HOSTERIAS -> logAndCount(hosterias, TipoAlojamiento.HOSTERIAS);
                            case HOTEL -> logAndCount(hoteles, TipoAlojamiento.HOTEL);
                            case HOTEL_APART -> logAndCount(apartHoteles, TipoAlojamiento.HOTEL_APART);
                            case HOTEL_RURAL -> logAndCount(hotelesRurales, TipoAlojamiento.HOTEL_RURAL);
                            case PENSION -> logAndCount(pensiones, TipoAlojamiento.PENSION);
                            case VIVIENDAS_TURISTICAS -> logAndCount(viviendasTuristicas, TipoAlojamiento.VIVIENDAS_TURISTICAS);
                        }
                    }
                ));
            mapa.put(APARTAMENTO_RURAL.toString(),apartamentosRurales);
            mapa.put(APART_TURISTICO.toString(),apartTuristicos);
            mapa.put(CAMPING.toString(),campings);
            mapa.put(CASA_HUESPEDES.toString(),casasHuespedes);
            mapa.put(CASA_RURAL.toString(),casasRurales);
            mapa.put(HOSTAL.toString(),hostales);
            mapa.put(HOSTERIAS.toString(),hosterias);
            mapa.put(HOTEL.toString(),hoteles);
            mapa.put(HOTEL_APART.toString(),apartHoteles);
            mapa.put(HOTEL_RURAL.toString(),hotelesRurales);
            mapa.put(PENSION.toString(),pensiones);
            mapa.put(VIVIENDAS_TURISTICAS.toString(),viviendasTuristicas);
        } catch (Exception e) {
            Log.error("Error al generar mapa con la cuenta. ", e);
        } finally {
            String message = String.format("Resultado: Total alojamientos turísticos en origen: %d. %s", listaFinal.size(), mapa);
            Log.info(message);
        }
    }

    private static synchronized void logAndCount(AtomicLong cuenta, TipoAlojamiento tipoAlojamiento) {
        cuenta.incrementAndGet();
        logCount(tipoAlojamiento);
    }

    private static void logCount(TipoAlojamiento alojamiento) {
        Log.debugf("%s %s.", CONTADO_UN, alojamiento);
    }

}
