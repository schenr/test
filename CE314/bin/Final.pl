############################## 
##take text from command line 
############################## 
 
use strict;
use warnings;

my $i = 0;
my $frequency = 0;
my $word;
#my words;
my %frequency; 


my $bigram;
my @bigrams;
my $cumulVal = 0;
open my $INFILE, "< HLT_data_mining.txt" or die "Can't open HLT.txt";
#open my $OUTFILE, ">> newFile.txt" or die "Can't open newFile.txt";
open (my $OUTFILE, '>>outFile.txt'); 
open (my $OUTFILE2, '>>outFile2.txt'); 
my $text = <$INFILE>;
while (my $line = <$INFILE> ) { 
	$text .= $line; 
	}  
	 
	############################################# 
	##Tokenizing the text & removing punctuations 
	############################################# 
	 
	$text =~ tr/a-zåàâäæçéèêëîïôöœùûüßA-ZÅÀÂÄÆÇÉÈÊËÎÏÔÖŒÙÛÜ’()\-,.?!:;/\n/cs; 
	$text =~ s/([,.?!:;()’\-])/\n$1\n/g; 
	$text =~ s/\n+/\n/g; 
	 
	########################### 
	##the file is tokenized NOW 
	########################### 
	 
	my @words = split(/\n/, $text); 
	#@words = split(/\s+/, $text); 
	 
	####################################### 
	##Prints unigrams and their frequencies 
	####################################### 
	 
	 
	print $OUTFILE "\n---------------------------------------------\n"; 
 
	print $OUTFILE "Frequency \t Unigrams \t Probability\n";  
	print $OUTFILE "\n---------------------------------------------\n"; 
	 
	for (my $i = 0; $i<= $#words; $i++) { 
		if (!exists($frequency{$words[$i]})){ 
			$frequency{$words[$i]} = 1; 
			$uniHash{$words[i]} = $words[0];
		} 
		else { 
			$frequency{$words[$i]}++; 
			$uniHash{$words[i]} = $words[++];
		} 
	} 
	 
	foreach $word (sort keys %frequency){ 
		
		 print $OUTFILE "$frequency{$word}\t\t $word\n";
	} 
	
	my %frequency_bigrams;
	my $frequency_bigrams;
	####################################### 
	##Prints bigrams and their frequencies 
	####################################### 
	 
	print $OUTFILE2 "\n---------------------------------------------\n"; 
 
	print $OUTFILE2 "Frequency \t Bigrams \t Probability\n";  
	print $OUTFILE2 "\n---------------------------------------------\n"; 
	my $probability = 1;
	for(my $i = 0; $i <$#words; $i++){ 
		$bigrams[$i] = $words[$i]. " ".$words[$i+1]; 
	
		
	} 
	for (my $i = 0; $i<= $#words; $i++) { 
		if (!exists($frequency_bigrams{$bigrams[$i]})){ 
			$frequency_bigrams{$bigrams[$i]} = 1; 
			
			
		} 
		else { 
			$frequency_bigrams{$bigrams[$i]}++; 
		} 
		
	} 
	
	foreach $bigram (sort keys %frequency_bigrams){ 
		$trial = ($bigram)/
		print $OUTFILE2 "$frequency_bigrams{$bigram}\t\t $bigram\n";
			 
	}
	
	#	for($i=0;$i<$#words;$i++) {

#this is the vital part - probability value calculation
#we need to add a few more values to this to compensate for the unknown
#we do this by making the count 1 if either is 0
 # my $denom = $frequency{$words[$i]};
  #my $numer = $bigrams[$i] = $words[$i].$words[$i+1];
  #if ( $denom == 0) {
#	$denom++;
 # }
  #if ($numer == 0 ) {
#	$numer++;
 # }
  #$cumulVal += log ($numer / $denom);
#}
print "The accumulated probability log calculated is " , $cumulVal , "\n";
print "\n";
	close $INFILE;
close $OUTFILE;
#close $OUTFILE2;