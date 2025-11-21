package org.wadajo.turismomadrid.domain.service;

import io.quarkus.logging.Log;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import org.wadajo.turismomadrid.application.repository.AlojamientoRepository;
import org.wadajo.turismomadrid.domain.document.AlojamientoDocument;
import org.wadajo.turismomadrid.domain.dto.cmadrid.enums.TipoAlojamiento;
import org.wadajo.turismomadrid.domain.model.AlojamientoTuristico;
import org.wadajo.turismomadrid.infrastructure.constants.Constants;
import org.wadajo.turismomadrid.infrastructure.mapper.AlojamientoDocumentMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.wadajo.turismomadrid.domain.dto.cmadrid.enums.TipoAlojamiento.*;

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

    public String guardarAlojamientosRemotosNuevosEnDb(){
        List<AlojamientoTuristico> todosLosAlojamientosEnRemoto = alojamientosService.getAlojamientosTotales();
        Log.infof("Total alojamientos turísticos en origen: %d", todosLosAlojamientosEnRemoto.size());
        AtomicLong cuentaGuardados = new AtomicLong();
        AtomicLong cuentaExistentes = new AtomicLong();

        var alojamientoDocumentList = new ArrayList<AlojamientoDocument>();

        for (AlojamientoTuristico unAlojamientoEnRemoto : todosLosAlojamientosEnRemoto) {
            final AlojamientoDocument unAlojamientoDocumentNuevo = convertToAlojamientoDocument(unAlojamientoEnRemoto);
            PanacheQuery<AlojamientoDocument> query= buscarSiAlojamientoExiste(unAlojamientoDocumentNuevo);
            if (query.firstResultOptional().isPresent()) {
                Log.debugf(unAlojamientoEnRemoto.toString()+" ya existe en la base de datos. Se omite su guardado.");
                cuentaExistentes.incrementAndGet();
                continue;
            }
            cuentaGuardados.incrementAndGet();
            alojamientoDocumentList.add(unAlojamientoDocumentNuevo);
        }

        alojamientoRepository.persist(alojamientoDocumentList);
        Log.infof("Guardados en DB %s apartamentos rurales.",contarAlojamientosTipo(alojamientoDocumentList, APARTAMENTO_RURAL));
        Log.infof("Guardados en DB %s apart turísticos.", contarAlojamientosTipo(alojamientoDocumentList, APART_TURISTICO));
        Log.infof("Guardados en DB %s campings.", contarAlojamientosTipo(alojamientoDocumentList, CAMPING));
        Log.infof("Guardados en DB %s casas de huéspedes.", contarAlojamientosTipo(alojamientoDocumentList, CASA_HUESPEDES));
        Log.infof("Guardados en DB %s casas rurales.", contarAlojamientosTipo(alojamientoDocumentList, CASA_RURAL));
        Log.infof("Guardados en DB %s hostales.", contarAlojamientosTipo(alojamientoDocumentList, HOSTAL));
        Log.infof("Guardados en DB %s hosterías.", contarAlojamientosTipo(alojamientoDocumentList, HOSTERIAS));
        Log.infof("Guardados en DB %s hoteles.", contarAlojamientosTipo(alojamientoDocumentList, HOTEL));
        Log.infof("Guardados en DB %s hoteles apart.",  contarAlojamientosTipo(alojamientoDocumentList, HOTEL_APART));
        Log.infof("Guardados en DB %s hoteles rurales.",  contarAlojamientosTipo(alojamientoDocumentList, HOTEL_RURAL));
        Log.infof("Guardados en DB %s pensiones.",  contarAlojamientosTipo(alojamientoDocumentList, PENSION));
        Log.infof("Guardados en DB %s viviendas turísticas.",  contarAlojamientosTipo(alojamientoDocumentList, VIVIENDAS_TURISTICAS));

        generarMapaConLaCuenta(todosLosAlojamientosEnRemoto);
        Log.infof("Han sido guardados en DB: %s alojamientos.", cuentaGuardados);
        Log.infof("Ya existían en DB: %s alojamientos que no se han guardado nuevamente.", cuentaExistentes);

        return "Han sido guardados en DB: "+ cuentaGuardados+" alojamientos.";
    }

    private static PanacheQuery<AlojamientoDocument> buscarSiAlojamientoExiste(AlojamientoDocument unAlojamientoDocumentNuevo) {
        return AlojamientoDocument.find(Constants.QUERY_EXISTE,
            unAlojamientoDocumentNuevo.via_nombre,
            unAlojamientoDocumentNuevo.via_tipo,
            Objects.requireNonNullElse(unAlojamientoDocumentNuevo.numero, ""),
            Objects.requireNonNullElse(unAlojamientoDocumentNuevo.codpostal, ""),
            unAlojamientoDocumentNuevo.localidad,
            Objects.requireNonNullElse(unAlojamientoDocumentNuevo.planta, ""),
            Objects.requireNonNullElse(unAlojamientoDocumentNuevo.bloque, ""),
            Objects.requireNonNullElse(unAlojamientoDocumentNuevo.puerta, ""));
    }

    private static long contarAlojamientosTipo(ArrayList<AlojamientoDocument> alojamientoDocumentList, TipoAlojamiento tipoAlojamiento) {
        return alojamientoDocumentList
            .stream()
            .filter(alojamientoDocument -> alojamientoDocument.alojamiento_tipo.equals(tipoAlojamiento.printValue))
            .count();
    }

    private AlojamientoDocument convertToAlojamientoDocument(AlojamientoTuristico alojamientoTuristicoEnRemoto) {
        return alojamientoDocumentMapper.convert(alojamientoTuristicoEnRemoto);
    }

    public void borrarTodo() {
        alojamientoRepository.deleteAll();
        Log.info("Borrada alojamientos");
    }

    public List<AlojamientoTuristico> getAlojamientosByType(TipoAlojamiento tipoValido) {
        var listaFiltrada = alojamientosService.getAlojamientosTotales().stream()
            .filter(alojamientoTuristico -> alojamientoTuristico.alojamiento_tipo() == tipoValido)
            .toList();
        generarMapaConLaCuenta(listaFiltrada);
        return listaFiltrada;
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

        try (var executor = Executors.newSingleThreadExecutor()) {
            listaFinal
                .forEach(unAlojamiento ->
                    executor.submit(() -> {
                        switch (unAlojamiento.alojamiento_tipo()){
                            case APARTAMENTO_RURAL -> logAndCount(apartamentosRurales);
                            case APART_TURISTICO -> logAndCount(apartTuristicos);
                            case CAMPING -> logAndCount(campings);
                            case CASA_HUESPEDES -> logAndCount(casasHuespedes);
                            case  CASA_RURAL -> logAndCount(casasRurales);
                            case HOSTAL -> logAndCount(hostales);
                            case HOSTERIAS -> logAndCount(hosterias);
                            case HOTEL -> logAndCount(hoteles);
                            case HOTEL_APART -> logAndCount(apartHoteles);
                            case HOTEL_RURAL -> logAndCount(hotelesRurales);
                            case PENSION -> logAndCount(pensiones);
                            case VIVIENDAS_TURISTICAS -> logAndCount(viviendasTuristicas);
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

    private static void logAndCount(AtomicLong cuenta) {
        cuenta.incrementAndGet();
    }

}
