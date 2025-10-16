package org.wadajo.turismomadrid.domain.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.wadajo.turismomadrid.application.repository.AlojamientoRepository;
import org.wadajo.turismomadrid.domain.document.AlojamientoDocument;
import org.wadajo.turismomadrid.domain.dto.cmadrid.enums.TipoAlojamiento;
import org.wadajo.turismomadrid.domain.model.AlojamientoTuristico;
import org.wadajo.turismomadrid.infrastructure.mapper.AlojamientoDocumentMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
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

        List<AlojamientoDocument> apartamentosRuralesDocumentList = new ArrayList<>();
        List<AlojamientoDocument> apartTuristicosDocumentList = new ArrayList<>();
        List<AlojamientoDocument> campingsDocumentList = new ArrayList<>();
        List<AlojamientoDocument> casasHuespedesDocumentList = new ArrayList<>();
        List<AlojamientoDocument> casasRuralesDocumentList = new ArrayList<>();
        List<AlojamientoDocument> hostalesDocumentList = new ArrayList<>();
        List<AlojamientoDocument> hosteriasDocumentList = new ArrayList<>();
        List<AlojamientoDocument> hotelesDocumentList = new ArrayList<>();
        List<AlojamientoDocument> hotelesApartDocumentList = new ArrayList<>();
        List<AlojamientoDocument> hotelesRuralesDocumentList = new ArrayList<>();
        List<AlojamientoDocument> pensionesDocumentList = new ArrayList<>();
        List<AlojamientoDocument> viviendasTuristicasDocumentList = new ArrayList<>();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            todosLosAlojamientosEnRemoto
                .forEach(alojamientoTuristicoEnRemoto ->
                    executor.submit(() -> {
                        cuenta.incrementAndGet();
                        switch (alojamientoTuristicoEnRemoto.alojamiento_tipo()) {
                            case APART_TURISTICO ->
                                apartTuristicosDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case APARTAMENTO_RURAL ->
                                apartamentosRuralesDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case CAMPING ->
                                campingsDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case CASA_HUESPEDES ->
                                casasHuespedesDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case CASA_RURAL ->
                                casasRuralesDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case HOSTAL ->
                                hostalesDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case HOSTERIAS ->
                                hosteriasDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case HOTEL ->
                                hotelesDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case HOTEL_APART ->
                                hotelesApartDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case HOTEL_RURAL ->
                                hotelesRuralesDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case PENSION ->
                                pensionesDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                            case VIVIENDAS_TURISTICAS ->
                                viviendasTuristicasDocumentList.add(convertToAlojamientoDocument(alojamientoTuristicoEnRemoto));
                        }
                    }));
        } catch (Exception e) {
            Log.error("Error al procesar alojamientos turísticos", e);
        }
        Log.info("Apart turísticos en lista guardar del servicio: " + apartTuristicosDocumentList.size());
        Log.info("Apartamentos rurales en lista guardar del servicio: " + apartamentosRuralesDocumentList.size());
        Log.info("Campings en lista guardar del servicio: " + campingsDocumentList.size());
        Log.info("Casas de huéspedes en lista guardar del servicio: " + casasHuespedesDocumentList.size());
        Log.info("Casas rurales en lista guardar del servicio: " + casasRuralesDocumentList.size());
        Log.info("Hostales en lista guardar del servicio: " + hostalesDocumentList.size());
        Log.info("Hosterías en lista guardar del servicio: " + hosteriasDocumentList.size());
        Log.info("Hoteles en lista guardar del servicio: " + hotelesDocumentList.size());
        Log.info("Hoteles apart en lista guardar del servicio: " + hotelesApartDocumentList.size());
        Log.info("Hoteles rurales en lista guardar del servicio: " + hotelesRuralesDocumentList.size());
        Log.info("Pensiones en lista guardar del servicio: " + pensionesDocumentList.size());
        Log.info("Viviendas turísticas en lista guardar del servicio: " + viviendasTuristicasDocumentList.size());
        Log.info("Suma de alojamientos en listas guardar del servicio: " +
            (apartTuristicosDocumentList.size() +
            apartamentosRuralesDocumentList.size() +
            campingsDocumentList.size() +
            casasHuespedesDocumentList.size() +
            casasRuralesDocumentList.size() +
            hostalesDocumentList.size() +
            hosteriasDocumentList.size() +
            hotelesDocumentList.size() +
            hotelesApartDocumentList.size() +
            hotelesRuralesDocumentList.size() +
            pensionesDocumentList.size() +
            viviendasTuristicasDocumentList.size()));

        try (var scope = StructuredTaskScope.open()) {
            scope.fork(() -> {
                alojamientoRepository.persist(apartamentosRuralesDocumentList);
                Log.info(String.format("Guardados en DB %s apartamentos rurales.", apartamentosRuralesDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(apartTuristicosDocumentList);
                Log.info(String.format("Guardados en DB %s apart turísticos.", apartTuristicosDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(campingsDocumentList);
                Log.info(String.format("Guardados en DB %s campings.", campingsDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(casasHuespedesDocumentList);
                Log.info(String.format("Guardados en DB %s casas de huéspedes.", casasHuespedesDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(casasRuralesDocumentList);
                Log.info(String.format("Guardados en DB %s casas rurales.", casasRuralesDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(hostalesDocumentList);
                Log.info(String.format("Guardados en DB %s hostales.", hostalesDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(hosteriasDocumentList);
                Log.info(String.format("Guardados en DB %s hosterías.", hosteriasDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(hotelesDocumentList);
                Log.info(String.format("Guardados en DB %s hoteles.", hotelesDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(hotelesApartDocumentList);
                Log.info(String.format("Guardados en DB %s hoteles apart.", hotelesApartDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(hotelesRuralesDocumentList);
                Log.info(String.format("Guardados en DB %s hoteles rurales.", hotelesRuralesDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(pensionesDocumentList);
                Log.info(String.format("Guardados en DB %s pensiones.", pensionesDocumentList.size()));
            });
            scope.fork(() -> {
                alojamientoRepository.persist(viviendasTuristicasDocumentList);
                Log.info(String.format("Guardados en DB %s viviendas turísticas.", viviendasTuristicasDocumentList.size()));
            });
            scope.join();
            generarMapaConLaCuenta(todosLosAlojamientosEnRemoto);
            return "Han sido guardados en DB: "+ cuenta+" alojamientos.";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private AlojamientoDocument convertToAlojamientoDocument(AlojamientoTuristico alojamientoTuristicoEnRemoto) {
        return alojamientoDocumentMapper.convert(alojamientoTuristicoEnRemoto);
    }

    public void borrarTodo() {
        alojamientoRepository.deleteAll();
        Log.info("Borrada alojamientosTuristicos");
    }

    public List<AlojamientoTuristico> getAlojamientosByType(TipoAlojamiento tipo) {
        var listaFiltrada = alojamientosService.getAlojamientosTotales().stream()
            .filter(alojamientoTuristico -> alojamientoTuristico.alojamiento_tipo() == tipo)
            .toList();
        generarMapaConLaCuenta(listaFiltrada);
        return listaFiltrada;
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
