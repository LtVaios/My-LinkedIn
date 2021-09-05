package uoa.di.tedbackend.user_impl;

import lombok.extern.slf4j.Slf4j;

import java.lang.Math;
import org.ejml.simple.*;
import org.springframework.core.annotation.Order;
import uoa.di.tedbackend.post_impl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean("UserDatabase")
    CommandLineRunner initDatabase(UserRepository repository, PostRepository prepo) {
        //repository.deleteAll();
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        //this is the website admin
        repository.save(new User(99,"admin@gmail.com",bCryptPasswordEncoder.encode("1234"),"Admin", "Admin", "00",true,
                "admin",
                "admin",
                "admin",
                "admin"));
        repository.save(new User(1,"bilbo@gmail.com",bCryptPasswordEncoder.encode("1234"),"Bilbo", "Baggins", "501-872-2516",false,
                "General Gaming, Boston, Developed full-stack web applications which processed, analyzed, and rendered data visually, " +
                        "Liaised with back end developers, front end developers, quality assurance testers, and CTO as needed.",
                "Department of informatics and telecommunications in university of Athens",
                "Google Compute Engine, " +
                        "Android and iOS App Development, " +
                        "HTML, CSS, JavaScript, PHP",
                "Senior android and ios developer"));
        repository.save(new User(2,"sam@outlook.com",bCryptPasswordEncoder.encode("1234"),"Sam", "Baggins", "501-872-2516",false,
                ".NET Developer Intern, " +
                        "BrainWild Global, " +
                        "Nov 2017–March 2019, " +
                        "Worked in an Agile team of .NET developers for a fast-paced software development firm, " +
                        "Implemented a smoothing function into a client CAD tool that improved UX scores 25%.",
                "Department of informatics and telecommunications in university of Athens",
                "Programming, ASP.NET MVC, client-side web development, SQL",
                ".NET full-stack developer"));
        repository.save(new User(3,"bob@gmail.com",bCryptPasswordEncoder.encode("1234"),"Bob", "White", "501-872-2516",false,
                "Senior Android Developer, " +
                        "Nozdyveh, Los Angeles, CA, " +
                        "2015–, " +
                        "Implemented features for various applications in Java, Kotlin, and C++, " +
                        "Determined architectural and product design details for open-ended tasks or specifications.",
                "M.I.T",
                "Software engineering, " +
                "Analytical skills ," +
                "Open-mindedness",
                "Senior software developer"));
        repository.save(new User(4,"michael@yahoo.com",bCryptPasswordEncoder.encode("1234"),"Michael", "Lee", "501-872-2516",false,
                "Maintained hardware performance, network connectivity, and software updates in a factory of 300+ workers, " +
                "Took over onboarding classes for factory hardware for all new employees.",
                "Stanford University",
                "IT Troubleshooting & Problem Solving, " +
                        "Onsite & Remote Technical Support, " +
                        "Up-to-Date Knowledge of Computer Operating Systems",
                "IT"));
        repository.save(new User(5,"carolyn@gmail.com",bCryptPasswordEncoder.encode("1234"),"carolyn", "Dupree", "501-872-2516",false,
                "Deloitte, New York, Performed quantitative and qualitative analysis to evaluate and diagnose business and management decisions across public, government, and private sectors, " +
                        "Recommended near-term and long-term solutions based on evaluation results, " +
                        "Implemented solutions to improve business efficiency, performance, cost control, and professionalization of employee base.",
                "Syracuse University, Syracuse, NY",
                "Staff Consultations & Meetings, " +
                        "Business Strategy & Outlook, " +
                        "Supply Chain Management & Optimization",
                "Big company Consultant"));
        repository.save(new User(6,"lukas.summers@mail.us",bCryptPasswordEncoder.encode("1234"),"Lukas", "Summers", "501-872-2516",false,
                "Managed the team of 15 accounting specialists responsible for payroll, accounts payable and receivable, and billing, " +
                        "Analyzed monthly and quarterly financial statements and presented the reports to the senior management, " +
                        "Performed budget forecasts and consistently worked on costs reductions.",
                "CUNY—Baruch College, The City University of New York",
                "Budget development, " +
                        "Budget tracking, " +
                        "Revenue projections, " +
                        "Corporate tax law, " +
                        "Negotiations, " +
                        "Analytical skills, " +
                        "Collaboration, " +
                        "Leadership, " +
                        "Problem solving, " +
                        "Time management",
                "Finance manager"));
        repository.save(new User(7,"brian.watkins@email.us",bCryptPasswordEncoder.encode("1234"),"Brian", "Watkins", "501-872-2516",false,
                "Processed company documentation, such as invoices and payment checks, " +
                        "Managed all purchase orders and monitored company budget by controlling project expenses, " +
                        "Performed administrative tasks, including filing, reporting, tagging fixed assets, etc.",
                "BA, Finance and Management, " +
                        "New Orleans College of Business Administration",
                "Financial statements, account analysis, QuickBooks, MS Excel, accounting terminology, mathematics.",
                "Finance manager assistant"));
        repository.save(new User(8,"jimmiedebrooke@mail.com",bCryptPasswordEncoder.encode("1234"),"Jimmie", "Debrooke", "501-872-2516",false,
                "Created high-quality 3D models of 20 game settings in Revit.\n" +
                        "Defined detailed environments with the use of 3ds Max toolset.\n" +
                        "Worked on produced content scaling.",
                "Ringling College of Art and Design, Sarasota, FL",
                "Adobe: Photoshop, Illustrator, InDesign, After Effects\n" +
                        "3D Software: Maya, Revit, 3D Studio Max\n" +
                        "Character Modeling\n" +
                        "Rigging\n" +
                        "Animation\n" +
                        "Collaboration\n" +
                        "Open-Mindedness\n" +
                        "Project Management",
                "3D software developer at Microsoft"));
        repository.save(new User(9,"selamawit.yemane@gmail.com",bCryptPasswordEncoder.encode("1234"),"Selamawit ", "Yemane", "501-872-2516",false,
                "Effectively supervised day-to-day front-end operations of a busy customer service department.\n" +
                        "Hired, trained, and mentored top customer service agents and front-end staff.\n" +
                        "Implemented ambitious customer satisfaction goals, and better-enabled customer service staff members to effectively meet them.",
                "Bachelor of Science in Retail Management\n" +
                        "\n" +
                        "Pace University, New York, NY",
                "Complaint Resolution\n" +
                        "Effective Communication\n" +
                        "Teambuilding & Training\n" +
                        "Cost Reduction & Waste Elimination",
                "Customer service at E-bay"));
        repository.save(new User(10,"john@aitken.me",bCryptPasswordEncoder.encode("1234"),"John", "Aitken", "501-872-2516",false,
                "Supervise multi-functional project teams of 10+ colleagues to develop creative and effective advertising concepts, from ideation through final projects.\n" +
                        "Incorporate and explore client suggestions and directives, resolve questions and concerns, oversee objections.\n" +
                        "Organize all creative materials to ensure their smooth transition to other departments.",
                "Bachelor of Fine Arts, Cornell University, Cum Laude\n" +
                        "\n" +
                        "New York City",
                "Graphic Design Software: Illustrator, Photoshop, InDesign, FreeHand, Corel Draw\n" +
                        "HTML/CSS\n" +
                        "Team Management",
                "Graphic designer at Amazon"));
        repository.save(new User(11,"james.integers@email.us",bCryptPasswordEncoder.encode("1234"),"James", "Integers", "501-872-2516",false,
                "Designed and implemented lesson plans that incorporated Algebra I and II, Geometry and Calculus.\n" +
                        "Assessed tests and reported and analyzed students results.\n" +
                        "Created interactive learning environment by using Smart board and mathematical software.\n" +
                        "Cooperated with fellow teachers and parents to ensure students have the best learning tools and environment for progress.\n" +
                        "Managed groups of up to 30 students and provided them with behavioral guidelines.",
                "2010–2013 MA, Math Education\n" +
                        "San Jose State University, San Jose, CA",
                "Creative lesson planning\n" +
                        "SMART board interactive displays\n" +
                        "Software (Microsoft Mathematics, Math Editor)\n" +
                        "Behavioral management\n" +
                        "Patience and understanding\n" +
                        "Enthusiasm and friendliness\n" +
                        "Communication skills",
                "Second grade math professor"));
        repository.save(new User(12,"janell.green@gmail.com",bCryptPasswordEncoder.encode("1234"),"Janell", "Green", "501-872-2516",false,
                "Maintained aircraft of all makes, models, and sizes, including wide-body aircraft, narrow-body aircraft, corporate and private jets, and commercial helicopters.\n" +
                        "Replaced, repaired, and maintained a variety of aircraft parts, including engine components, electronics in the flight deck, and fuselage parts.\n" +
                        "Performed routine maintenance and testing of various aircraft components to ensure flight safety.",
                "Aviation Institute of Maintenance, Philadelphia, PA",
                "Aviation Maintenance Tools (e.g., Micrometer, Scientific Calculator, Soldering Iron, Deburring Tools)\n" +
                        "Ground Service Equipment Maintenance\n" +
                        "In-Flight Entertainment and Connectivity Systems\n" +
                        "Electronic Avionics\n" +
                        "PLC / Motion Integration & Programming",
                "Aircraft engineer"));
        repository.save(new User(13,"florence.whitmer@gmail.com",bCryptPasswordEncoder.encode("1234"),"Florence", "Whitmer", "501-872-2516",false,
                "Created marketing objectives against business unit priorities.\n" +
                        "Implemented consumer research and market trends into actionable insights for sell-in and sell-thru communication strategies.\n" +
                        "Created full 360-go-to market plans, content roadmaps and oversaw the creation of the tools for all apparel.\n" +
                        "Managed a high performing and engaged team of 5 marketing specialists and analysts.",
                "MBA\n" +
                        "Cornell University, New York, NY\n" +
                        "2015\n" +
                        "B.A., Marketing\n" +
                        "Cornell University, New York, NY\n" +
                        "2011",
                "Analytical skills\n" +
                        "Communication\n" +
                        "Interpersonal skills\n" +
                        "Marketing principles, practices, and procedures\n" +
                        "Market research",
                "Marketing team manager at Microsoft"));
        repository.save(new User(14,"bob@gmail.com",bCryptPasswordEncoder.encode("1234"),"Bob", "Baggins", "501-872-2516",false,
                "Prepared multi-state tax returns in compliance with US federal and state law.\n" +
                        "Researched tax regulations and analyzed financial statements to ensure documentation correctness.\n" +
                        "Monitored monthly company expenses for 40 ongoing projects.\n" +
                        "Took part in quarterly financial planning and implemented tax strategies to minimize the financial risks of the company.",
                "MA, Accountancy\n" +
                        "University of San Francisco\n" +
                        "2011-2014\n" +
                        "BA, Accounting and Science in Taxation\n" +
                        "University of San Francisco\n" +
                        "2008-2011",
                "Financial statements\n" +
                        "Payroll taxes\n" +
                        "Corporate tax law\n" +
                        "Tax returns\n" +
                        "Mathematics\n" +
                        "Attention to detail\n" +
                        "Analytical skills\n" +
                        "Problem-solving\n" +
                        "Time management",
                "Financial manager at SpaceX"));
        repository.save(new User(15,"jamie.ian@gmail.com",bCryptPasswordEncoder.encode("1234"),"Jamie", "Iannone", "501-872-2516",false,
                " CEO of SamsClub.com and executive vice president of membership and technology, rising to chief operating officer (COO) for U.S. e-commerce for the parent company, Walmart.",
                "Bachelor's degree in engineering from Princeton University, and an MBA from Stanford Graduate School of Business.",
                "Past Ceo of a lot of companies",
                "Current Ceo of E-bay"));
        return args -> {
            log.info("Preloading user database");
        };
    }

    void MF(){
        SimpleMatrix userMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {1},
                        new double[] {2},
                        new double[] {3},
                        new double[] {4},
                        new double[] {5},
                        new double[] {6}

                }
        );

        SimpleMatrix postMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {1, 2, 3, 4}
                }
        );

        SimpleMatrix dataMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {5, 3, 0, 1},
                        new double[] {4, 0, 0, 1},
                        new double[] {1, 1, 0, 5},
                        new double[] {1, 0, 0, 4},
                        new double[] {0, 1, 5, 4},
                        new double[] {2, 1, 3, 0}
                }
        );

        int k=3;
        double err=999999,e,prev_err;
        double h=0.00001;
        double x_;
        Random rand = new Random();
        SimpleMatrix V = SimpleMatrix.random_DDRM(userMatrix.numRows(),k,1,50,rand);
        SimpleMatrix F = SimpleMatrix.random_DDRM(k,postMatrix.numCols(),1,50,rand);
        for(int iter=0; iter<=10000; iter++){
            for(int i=0; i<dataMatrix.numRows(); i++){
                for(int j=0; j<dataMatrix.numCols(); j++) {
                    if (dataMatrix.get(i, j) > 0){
                        x_=0;
                        for(int n=0; n<k; n++)
                            x_ += V.get(i,n)*F.get(n,j);
                        e = dataMatrix.get(i, j) - x_;
                        for (int n = 0; n < k; n++) {
                            V.set(i, n, V.get(i, n) + h * 2 * e * F.get(n, j));
                            F.set(n, j, F.get(n, j) + h * 2 * e * V.get(i, n));
                        }
                    }
                }
            }
            prev_err=err;
            err=0;
            for(int i=0; i<dataMatrix.numRows(); i++){
                for(int j=0; j<dataMatrix.numCols(); j++) {
                    if (dataMatrix.get(i, j) > 0) {
                        x_=0;
                        for(int n=0; n<k; n++)
                            x_ += V.get(i,n)*F.get(n,j);
                        err += Math.pow(dataMatrix.get(i, j) - x_, 2);
                    }
                }
            }
            if(prev_err <= err ){
                System.out.println("Iter: "+iter);
                break;
            }
        }
        System.out.println("err: "+err);
        System.out.println("Initial: ");
        dataMatrix.print();
        SimpleMatrix res = V.mult(F);
        System.out.println("Result: ");
        res.print();
    }
}
