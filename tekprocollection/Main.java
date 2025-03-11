import java.util.*;
import java.util.concurrent.*;

// 2. Record
record Student(int id, String name, Optional<String> email, String program) {}

class AdmissionSystem {
    // 1. List, Set, dan Map
    private final List<Student> students = new CopyOnWriteArrayList<>();
    private final Set<Integer> studentIds = ConcurrentHashMap.newKeySet();
    private final Map<Integer, Student> studentMap = new ConcurrentHashMap<>();

    // 4. Concurrent Collections
    private final Queue<Student> admissionQueue = new ConcurrentLinkedQueue<>();

    // 6. Immutable Collection
    private static final Set<String> PROGRAMS = Set.of("Informatika", "Elektro", "Mesin", "Sipil");

    // Mendaftarkan mahasiswa baru
    public void registerStudent(int id, String name, String email, String program) {
        if (!PROGRAMS.contains(program)) {
            System.out.println("Program studi tidak valid!");
            return;
        }
        Student student = new Student(id, name, Optional.ofNullable(email), program);

        if (studentIds.add(id)) {
            students.add(student);
            studentMap.put(id, student);
            admissionQueue.offer(student); // 5. Menambahkan ke antrean penerimaan
            System.out.println("Mahasiswa " + name + " berhasil didaftarkan.");
        } else {
            System.out.println("ID sudah digunakan!");
        }
    }

    // Memproses antrean mahasiswa yang sudah mendaftar
    public void processAdmissions() {
        while (!admissionQueue.isEmpty()) {
            Student student = admissionQueue.poll();
            System.out.println("Memproses: " + student.name() + " untuk program " + student.program());
        }
    }

    // Menampilkan semua mahasiswa yang terdaftar
    public void displayStudents() {
        System.out.println("\nDaftar Mahasiswa:");
        for (Student student : students) {
            System.out.println("ID: " + student.id() + ", Nama: " + student.name() +
                    ", Email: " + student.email().orElse("Tidak ada email") +
                    ", Program: " + student.program());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        AdmissionSystem system = new AdmissionSystem();

        // Mendaftarkan mahasiswa
        system.registerStudent(101, "BABA", "babayaga@mail.com", "Informatika");
        system.registerStudent(102, "Arkan", "arney1@mail.com", "Informatika");
        system.registerStudent(103, "Vito", null, "Elektro"); // 3. Optional
        system.registerStudent(104, "Farhan", "farhan@mail.com", "Mesin");

        system.displayStudents();

        system.processAdmissions();
    }
}

